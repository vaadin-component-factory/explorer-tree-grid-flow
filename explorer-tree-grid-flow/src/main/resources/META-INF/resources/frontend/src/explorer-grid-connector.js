/**
 * Override the private method __getRowModel of the Vaadin Grid.
 *
 *
 * @type {{initLazy: Window.Vaadin.Flow.explorerGridConnector.initLazy}}
 */
window.Vaadin.Flow.explorerGridConnector = {
    initLazy: function (c) {
        // Check whether the connector was already initialized
        if (c.$explorerconnector) {
            return;
        }
        c.$explorerconnector = {};
        /*
         * Override __getRowModel() to add some information in the row model.
         * last, first, parentlines
         */
        c.__super__getRowModel = c.__getRowModel;

        const lasts = [];

        c.__getRowModel = function __getRowModel(row) {
            // don't call this._getIndexLevel(row.index) to reuse cache, scaledIndex
            let parentlines = [];
            let {cache, index} = this._dataProviderController.getFlatIndexContext(row.index);

            let level = c.__super__getRowModel.call(this, row).level;

            let last = (index === cache.size - 1) || (index < cache.size - 1 && cache.items[index + 1] && cache.items[index + 1].level < level);

            let hasChildren = c.__super__getRowModel.call(this, row).hasChildren;
            const isExpanded = this._isExpanded(row._item);

            if (hasChildren && isExpanded) {
                // Scan until finding element with the same level
                let found = false;
                for (let i = index + 1; i < cache.size; i++) {
                    const item = cache.items[i];
                    if (item.level === level) {
                        found = true;
                        break;
                    } else if (item.level < level) {
                        break;
                    }
                }
                if (!found) {
                    last = true;
                    const item = cache.items[index];
                    if (item && !lasts.includes(item.key)) {
                        lasts.push(item.key);
                    }
                }
            }

            // Find if the item has a parents with no further siblings 
            // and their levels
            const parentLevels = [];
            let lastParent = false;
            let parentLevel = level;
            if (index > 0) {
                for (let i = index - 1; i >= 0; i--) {
                    const item = cache.items[i];
                    if (item.level < parentLevel) {
                        parentLevel = item.level;
                        if (lasts.includes(item.key)) {
                            parentLevels.push(parentLevel);
                            lastParent = true;
                        }
                        if (parentLevel == 0) {
                            break;
                        }
                    }
                }
            }

            for (let i = 0; i < level; i++) {
                parentlines.push(true);
            }
            for (let i = 0; i < level; i++) {
                if (lastParent && parentLevels.includes(i)) {
                    parentlines[i] = false;
                } else {
                    parentlines[i] = true;
                }
            }
            const first = (row.index === 0);

            return {
                index: row.index,
                item: row._item,
                level: level,
                first: first,
                last: last,
                hasChildren: hasChildren,
                parentlines: parentlines,
                expanded: isExpanded,
                selected: this._isSelected(row._item),
                detailsOpened:
                    !!(this._rowDetailsTemplate || this.rowDetailsRenderer) && this._isDetailsOpened(row._item)
            };
        }
    }
}