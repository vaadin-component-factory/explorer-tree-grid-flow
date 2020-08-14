package org.vaadin.explorer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataCommunicator;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.Department;
import org.vaadin.explorer.bean.DepartmentData;
import org.vaadin.explorer.bean.Person;

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
