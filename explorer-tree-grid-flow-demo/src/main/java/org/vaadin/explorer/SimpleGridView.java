package org.vaadin.explorer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataCommunicator;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.Department;
import org.vaadin.explorer.bean.DepartmentData;
import org.vaadin.explorer.bean.Person;

/**
 * @author jcgueriaud
 */
@CssImport(value="src/vaadin-tree-grid.css", themeFor="vaadin-grid-tree-toggle")
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
        grid.addThemeName("connectors");
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addColumn(TemplateRenderer
                .<Department> of("<vaadin-grid-tree-toggle theme='connectors' "
                        + "leaf='[[item.leaf]]' last='[[item.last]]' expanded='{{expanded}}' " +
                        "level='[[level]]'>[[item.name]]"
                        + "</vaadin-grid-tree-toggle")
                .withProperty("leaf",
                        item -> {
                            Integer index = grid.getDataCommunicator().getIndex(item);
                            Department parentItem = grid.getDataCommunicator().getParentItem(item);
                            String key = grid.getDataCommunicator().getKeyMapper().key(item);
                            return !grid.getDataCommunicator().hasChildren(item);
                        }).withProperty("last",
                        item -> {
                            if  (item.getName().equals("Marketing") || item.getName().equals("Brand Experience")) {
                                return true;
                            } else {
                                return false;
                            }
                        })
                .withProperty("name",
                        value -> value.getName()))
                .setHeader("Department Name");
        grid.setSizeFull();
        //grid.setClassNameGenerator();
        return grid;
    }
}
