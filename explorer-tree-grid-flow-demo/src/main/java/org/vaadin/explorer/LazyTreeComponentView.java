package org.vaadin.explorer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.DummyFile;

import static org.vaadin.explorer.TreeGridUtil.getDummyFileTreeGrid;

/**
 * Example with a component
 */
@Route(value = "component-explorer-tree", layout = MainLayout.class)
public class LazyTreeComponentView extends Div {


    public LazyTreeComponentView() {
        setSizeFull();
        TreeGrid<DummyFile> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<DummyFile> buildGrid() {
        ExplorerTreeGrid<DummyFile> grid = new ExplorerTreeGrid<>();
        grid.addComponentHierarchyColumn(value -> {
            Icon icon = VaadinIcon.FOLDER_OPEN_O.create();
            icon.setColor("var(--lumo-contrast-50pct)");
            return new Span(icon, VaadinIcon.CLIPBOARD.create(), new Span(value.getFilename()));
        }).setHeader("Department Name");

        return getDummyFileTreeGrid(grid);
    }
}
