package org.vaadin.explorer;

import com.vaadin.flow.component.html.Div;
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
                        /*System.out.println("getOffset " + query.getOffset());*/
                        return DummyFileService.fetchChildren(query.getParent(), query.getOffset()).stream();
                    }
                };

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        return grid;
    }
}
