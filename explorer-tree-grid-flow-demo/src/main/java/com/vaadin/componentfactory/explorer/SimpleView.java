package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DepartmentData;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.componentfactory.explorer.bean.Department;

/**
 * Basic example with setItems
 */
@Route(value = "", layout = MainLayout.class)
public class SimpleView extends Div {


    public SimpleView() {
        setSizeFull();
        TreeGrid<Department> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<Department> buildGrid() {
        DepartmentData departmentData = new DepartmentData();
        ExplorerTreeGrid<Department> grid = new ExplorerTreeGrid<>();
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addHierarchyColumn(Department::getName).setHeader("Department Name");
        grid.setSizeFull();
        grid.expand(departmentData.getRootDepartments());
        grid.setHierarchicalSelectionMode(Grid.SelectionMode.MULTI);
        return grid;
    }
}
