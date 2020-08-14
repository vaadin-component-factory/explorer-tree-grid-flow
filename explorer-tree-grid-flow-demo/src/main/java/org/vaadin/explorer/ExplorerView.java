package org.vaadin.explorer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.DummyFile;

import static org.vaadin.explorer.TreeGridUtil.getDummyFileTreeGrid;

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
