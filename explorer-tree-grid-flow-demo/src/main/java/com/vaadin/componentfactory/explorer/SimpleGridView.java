package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DepartmentData;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.componentfactory.explorer.bean.Department;

/**
 * Basic example with setItems for the Vaadin TreeGrid
 */
@Route(value = "simple", layout = MainLayout.class)
public class SimpleGridView extends Div {

    public SimpleGridView() {
        setSizeFull();
        TreeGrid<Department> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<Department> buildGrid() {
        DepartmentData departmentData = new DepartmentData();
        TreeGrid<Department> grid = new TreeGrid<>();
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addHierarchyColumn(Department::getName).setHeader("Department Name");
        grid.setSizeFull();
        grid.expand(departmentData.getRootDepartments());
        return grid;
    }
}
