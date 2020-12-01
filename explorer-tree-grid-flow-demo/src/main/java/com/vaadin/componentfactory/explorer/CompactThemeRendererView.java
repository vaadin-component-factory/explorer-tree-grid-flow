package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DummyFile;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;

/**
 * Example with a custom theme variant "compact" to reduce the height of each rows
 */
@CssImport(value="./src/compact-vaadin-tree-grid.css", themeFor = "vaadin-grid")
@Route(value = "compact", layout = MainLayout.class)
public class CompactThemeRendererView extends Div {


    public CompactThemeRendererView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        ExplorerTreeGrid<DummyFile> grid = new ExplorerTreeGrid<>();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addHierarchyColumn(DummyFile::getFilename, DummyFile::getIcon).setHeader("File Name");
        grid.addColumn(DummyFile::getCode).setHeader("Code");
        return TreeGridUtil.getDummyFileTreeGrid(grid);
    }
}
