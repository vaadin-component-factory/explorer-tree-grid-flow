package org.vaadin.explorer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.DummyFile;

import static org.vaadin.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * Example with a custom theme variant "compact" to reduce the height of each rows
 */
@Route(value = "dark-theme", layout = MainLayout.class)
public class DarkThemeRendererView extends Div {

    public DarkThemeRendererView() {
        getElement().setAttribute("theme", "dark");
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        ExplorerTreeGrid<DummyFile> grid = new ExplorerTreeGrid<>();
        grid.addThemeName("custom-theme");
        grid.addHierarchyColumn(DummyFile::getFilename, DummyFile::getIcon).setHeader("File Name");
        return getDummyFileTreeGrid(grid);
    }
}
