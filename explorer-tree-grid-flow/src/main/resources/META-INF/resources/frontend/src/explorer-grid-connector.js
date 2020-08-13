
window.Vaadin.Flow.explorerGridConnector = {
    initLazy: function (c) {
        console.log("init explorerGridConnector");
        // Check whether the connector was already initialized
        if (c.$explorerconnector) {
            return;
        }
        c.$explorerconnector = {};
        c.__getRowModel = function __getRowModel(row) {
            // const level = this._getIndexLevel(row.index);
            let parentlines = [];
            let {cache, scaledIndex} = this._cache.getCacheAndIndex(row.index);
            const last = (scaledIndex === cache.size - 1);

            if (last) {
                debugger;
            }
            let level = 0;
            while (cache.parentCache) {
                // there is a parent
                let parentKey = cache.parentItem.key;
                if ((cache.parentCache.items[cache.parentCache.size - 1] != null)  && cache.parentCache.items[cache.parentCache.size - 1].key === cache.parentItem.key) {
                    parentlines.unshift(0); // no line
                } else {
                    parentlines.unshift(1);  // display line
                }
                cache = cache.parentCache;
                level++;
            }
            const first = (row.index === 0);

            return {
                index: row.index,
                item: row._item,
                level: level,
                first: first,
                last: last,
                parentlines: parentlines,
                expanded: this._isExpanded(row._item),
                selected: this._isSelected(row._item),
                detailsOpened:
                    !!(this._rowDetailsTemplate || this.rowDetailsRenderer) && this._isDetailsOpened(row._item)
            };
        }
    }
}