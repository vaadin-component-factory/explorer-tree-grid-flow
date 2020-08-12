package org.vaadin.explorer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.ValueProvider;

import java.io.Serializable;

@Uses(Icon.class)
@CssImport("src/explorer-tree-toggle.js")
@CssImport(value="src/explorer-tree-grid.css", themeFor="explorer-tree-grid-toggle")
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
     * @param valueProvider
     * @return
     */
    @Override
    public Column<T> addHierarchyColumn(ValueProvider<T, ?> valueProvider) {
        addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        return addColumn(TemplateRenderer
                .<T> of("<explorer-tree-grid-toggle theme='connectors' "
                        + "leaf='[[item.leaf]]' last='[[last]]' first='[[first]]' parentlines='{{parentlines}}' expanded='{{expanded}}' " +
                        "level='[[level]]'><iron-icon style='margin-left: 0.3em;margin-right: 0.3em; color: var(--color-border); --iron-icon-height: 1em;--iron-icon-width: 1em;' icon='vaadin:folder-o'></iron-icon> [[item.name]]  "
                        + "</explorer-tree-grid-toggle>") // [[index]] - L[[last]] - P[[previouslevel]]  - N[[nextlevel]] -C[[level]] - [[item.name]] - parentlines-[[parentlines]]
                .withProperty("leaf",
                        item -> {
                            return !getDataCommunicator().hasChildren(item);
                        })
                .withProperty("name", valueProvider));
    }

}
