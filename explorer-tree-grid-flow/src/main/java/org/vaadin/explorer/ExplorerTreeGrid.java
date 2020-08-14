package org.vaadin.explorer;

/*
 * #%L
 * explorer-tree-grid-flow
 * %%
 * Copyright (C) 2020 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 * 
 * See the file license.html distributed with this software for more
 * information about licensing.
 * 
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

import java.io.Serializable;

/**
 * A grid component for displaying hierarchical tabular data
 * and displaying connectors between parent and children
 *
 * @param <T>
 *            the grid bean type
 */
@Uses(Icon.class)
@CssImport("./src/explorer-tree-toggle.js")
@JavaScript("./src/explorer-grid-connector.js")
public class ExplorerTreeGrid<T> extends TreeGrid<T> {

    protected void initConnector() {
        super.initConnector();
        (getUI().orElseThrow(() -> new IllegalStateException("Connector can only be initialized for an attached Grid")))
                .getPage().executeJs("window.Vaadin.Flow.explorerGridConnector.initLazy($0)", new Serializable[]{this.getElement()});
    }


    /**
     * To update with prefix icon, suffix icon
     *
     * @param labelProvider
     * @return
     */
    @Override
    public Column<T> addHierarchyColumn(ValueProvider<T, ?> labelProvider) {
        return addHierarchyColumn(labelProvider, object -> "vaadin:folder-open-o");
    }
    /**
     * To update with prefix icon, suffix icon
     *
     * @param labelProvider
     * @param iconProvider
     * @return
     */
    public Column<T> addHierarchyColumn(ValueProvider<T, ?> labelProvider, ValueProvider<T, ?> iconProvider) {
        Column<T> column = addColumn(TemplateRenderer
                .<T>of("<explorer-tree-grid-toggle "
                        + "leaf='[[item.leaf]]' last='[[last]]' icon='[[item.icon]]' first='[[first]]' parentlines='{{parentlines}}' expanded='{{expanded}}' " +
                        "level='[[level]]'>[[item.name]]  "
                        + "</explorer-tree-grid-toggle>")
                .withProperty("leaf",
                        item -> {
                            return !getDataCommunicator().hasChildren(item);
                        })
                .withProperty("icon", iconProvider)
                .withProperty("name", labelProvider));

        SerializableComparator<T> comparator = (a, b) -> {
            return compareMaybeComparables(labelProvider.apply(a), labelProvider.apply(b));
        };
        column.setComparator(comparator);
        return column;

    }

    public <V extends Component> Column<T> addComponentHierarchyColumn(
            ValueProvider<T, V> componentProvider) {
        return addColumn(new ExplorerTreeHierarchyColumnComponentRenderer<V, T>(
                componentProvider).withProperty("leaf",
                item -> !getDataCommunicator().hasChildren(item)));
    }
}
