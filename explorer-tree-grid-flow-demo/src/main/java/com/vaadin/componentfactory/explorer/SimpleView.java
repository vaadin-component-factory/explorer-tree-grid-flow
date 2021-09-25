package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.Department;
import com.vaadin.componentfactory.explorer.bean.DepartmentData;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import java.util.Collection;

/**
 * Basic example with setItems
 */
@Route(value = "", layout = MainLayout.class)
public class SimpleView extends Div {
    private TreeDataProvider<Department> treeDataProvider;

    public SimpleView() {
        setSizeFull();
        TreeGrid<Department> grid = buildGrid();
        TextField field = new TextField();
        field.addValueChangeListener(e -> {
            treeDataProvider.setFilter(d -> d.getName().contains(e.getValue()));
        });
        add(field);
        add(grid);
    }

    private TreeGrid<Department> buildGrid() {
        DepartmentData departmentData = new DepartmentData();
        ExplorerTreeGrid<Department> grid = new ExplorerTreeGrid<>();
        ValueProvider<Department, Collection<Department>> childItemProvider = departmentData::getChildDepartments;
        treeDataProvider = new TreeDataProvider<>(
            new TreeData<Department>().addItems(departmentData.getRootDepartments(),
                childItemProvider));
        grid.setDataProvider(treeDataProvider);
        grid.addHierarchyColumn(Department::getName).setHeader("Department Name");
        grid.setSizeFull();
        grid.expand(departmentData.getRootDepartments());
        grid.setHierarchicalSelectionMode(Grid.SelectionMode.MULTI);

        return grid;
    }
}
