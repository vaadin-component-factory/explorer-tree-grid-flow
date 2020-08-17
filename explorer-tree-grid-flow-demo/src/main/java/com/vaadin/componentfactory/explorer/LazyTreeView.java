package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DummyFile;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;

import static com.vaadin.componentfactory.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * @author jcgueriaud
 */
@Route(value = "explorer-tree", layout = MainLayout.class)
public class LazyTreeView extends Div {


    public LazyTreeView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        TreeGrid<DummyFile> grid = new TreeGrid<>();
        grid.addHierarchyColumn(DummyFile::getFilename).setHeader("File Name");

        return getDummyFileTreeGrid(grid);
    }
}
