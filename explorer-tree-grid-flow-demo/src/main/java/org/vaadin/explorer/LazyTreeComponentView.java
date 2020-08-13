package org.vaadin.explorer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.DummyFile;
import org.vaadin.explorer.service.DummyFileService;

import java.util.stream.Stream;

/**
 * @author jcgueriaud
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

        HierarchicalDataProvider<DummyFile, Void> dataProvider =
                new AbstractBackEndHierarchicalDataProvider<DummyFile, Void>() {

                    @Override
                    public int getChildCount(HierarchicalQuery<DummyFile, Void> query) {
                        return DummyFileService.getChildCount(query.getParent());
                    }

                    @Override
                    public boolean hasChildren(DummyFile item) {
                        return DummyFileService.hasChildren(item);
                    }

                    @Override
                    protected Stream<DummyFile> fetchChildrenFromBackEnd(
                            HierarchicalQuery<DummyFile, Void> query) {
                        return DummyFileService.fetchChildren(query.getParent(), query.getOffset()).stream();
                    }
                };

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        return grid;
    }
}
