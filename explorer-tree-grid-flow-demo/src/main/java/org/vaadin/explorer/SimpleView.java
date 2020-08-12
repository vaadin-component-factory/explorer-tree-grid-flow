package org.vaadin.explorer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.Department;
import org.vaadin.explorer.bean.DepartmentData;
import org.vaadin.explorer.bean.Person;
import org.vaadin.explorer.bean.PersonUtil;

/**
 * @author jcgueriaud
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
        TreeGrid<Department> grid = new ExplorerTreeGrid<>();
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addHierarchyColumn(value -> value.getName()).setHeader("Department Name");
        grid.setSizeFull();
        return grid;
    }
}
