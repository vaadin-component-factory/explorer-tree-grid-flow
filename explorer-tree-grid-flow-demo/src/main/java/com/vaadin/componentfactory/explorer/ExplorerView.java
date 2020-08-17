package com.vaadin.componentfactory.explorer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.componentfactory.explorer.bean.DummyFile;

import static com.vaadin.componentfactory.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * Example with a text and an icon
 */
@Route(value = "explorer", layout = MainLayout.class)
public class ExplorerView extends Div {

    public ExplorerView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        ExplorerTreeGrid<DummyFile> grid = new ExplorerTreeGrid<>();
        grid.addHierarchyColumn(DummyFile::getFilename, DummyFile::getIcon).setHeader("File Name");

        return getDummyFileTreeGrid(grid);
    }

}
