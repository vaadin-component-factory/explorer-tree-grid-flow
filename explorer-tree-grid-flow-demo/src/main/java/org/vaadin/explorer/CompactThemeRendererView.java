package org.vaadin.explorer;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.Department;
import org.vaadin.explorer.bean.DepartmentData;

/**
 * @author jcgueriaud
 */
@Route(value = "compact", layout = MainLayout.class)
public class CompactThemeRendererView extends Div {


    public CompactThemeRendererView() {
        setSizeFull();
        TreeGrid<Department> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<Department> buildGrid() {
        DepartmentData departmentData = new DepartmentData();
        ExplorerTreeGrid<Department> grid = new ExplorerTreeGrid<>();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addComponentHierarchyColumn(value -> {
            Icon icon = VaadinIcon.FOLDER_OPEN_O.create();
            icon.setColor("var(--lumo-contrast-50pct)");
            return new Span(icon, new Span(value.getName()));
        }).setHeader("Department Name");
        grid.setSizeFull();
        grid.expand(departmentData.getRootDepartments());
        return grid;
    }
}
