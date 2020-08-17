package com.vaadin.componentfactory.explorer;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.componentfactory.explorer.bean.DummyFile;

import static com.vaadin.componentfactory.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * Example with a custom theme variant "compact" to reduce the height of each rows
 */
@Route(value = "custom-theme", layout = MainLayout.class)
public class CustomThemeRendererView extends Div {

    public CustomThemeRendererView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        ExplorerTreeGrid<DummyFile> grid = new ExplorerTreeGrid<>();
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addThemeName("custom-theme");

        grid.addHierarchyColumn(DummyFile::getFilename, DummyFile::getIcon).setHeader("File Name");
        grid.addColumn(DummyFile::getCode).setHeader("Code");
        return getDummyFileTreeGrid(grid);
    }
}
