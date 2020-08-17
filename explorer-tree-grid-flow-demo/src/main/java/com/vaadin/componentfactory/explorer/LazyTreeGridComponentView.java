package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DummyFile;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;

import static com.vaadin.componentfactory.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * Example with a component
 */
@Route(value = "component-explorer-tree-grid", layout = MainLayout.class)
public class LazyTreeGridComponentView extends Div {

    public LazyTreeGridComponentView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        TreeGrid<DummyFile> grid = new TreeGrid<>();
        grid.addComponentHierarchyColumn(value -> {
            Icon icon = VaadinIcon.FOLDER_OPEN_O.create();
            icon.setColor("var(--lumo-contrast-50pct)");
            return new Span(icon, VaadinIcon.CLIPBOARD.create(), new Span(value.getFilename()));
        }).setHeader("Department Name");

        return getDummyFileTreeGrid(grid);
    }
}
