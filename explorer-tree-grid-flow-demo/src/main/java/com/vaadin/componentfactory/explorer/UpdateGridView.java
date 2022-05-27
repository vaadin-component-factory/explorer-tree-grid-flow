package com.vaadin.componentfactory.explorer;

import com.vaadin.componentfactory.explorer.bean.DepartmentData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.Route;
import com.vaadin.componentfactory.explorer.bean.Department;

import java.util.stream.Stream;

/**
 * This view helps to test the behavior of the Explorer Tree Grid
 * when items are updated, added or removed.
 *
 * If you remove the last child of a node then the child is still in the view:
 * https://github.com/vaadin/vaadin-grid-flow/issues/1089
 *
 * @author jcgueriaud
 */
@Route(value = "update-grid", layout = MainLayout.class)
public class UpdateGridView extends VerticalLayout {

    private DepartmentData departmentData = new DepartmentData();

    // Form
    private Binder<Department> binder = new Binder<>(Department.class);
    private IntegerField id = new IntegerField("id");
    private TextField name = new TextField("name");
    private TextField manager = new TextField("manager");
    private Checkbox archive = new Checkbox("archive");

    // Actions
    private Button removeButton = new Button("Remove selected Item");
    private Button addButton = new Button("Add child Item");

    private int nextid = 300;

    public UpdateGridView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        TreeGrid<Department> grid = buildGrid();
        addAndExpand(grid);
        binder.bindInstanceFields(this);
        // every time the value is updated, update the node in the tree
        binder.addValueChangeListener(event -> {
            grid.getDataProvider().refreshItem(binder.getBean());
        });
        id.setReadOnly(true);

        HorizontalLayout formLayout = new HorizontalLayout(id, name, manager, archive, removeButton, addButton);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        formLayout.setPadding(true);
        add(formLayout);
        grid.addSelectionListener(selection ->
                selection.getFirstSelectedItem().ifPresent(selected ->
                        binder.setBean(selected)));

        configureActions(grid);
        add(VaadinIcon.CLIPBOARD.create());
    }

    private void configureActions(TreeGrid<Department> grid) {
        // remove the item and deep refresh the parent
        removeButton.addClickListener(buttonClickEvent -> {
            grid.getSelectionModel().getFirstSelectedItem().ifPresent(
                    selected -> {
                        departmentData.getDepartments().remove(selected);
                        if (binder.getBean().getParent() != null) {
                            grid.getDataProvider().refreshItem(binder.getBean().getParent(), true);
                        } else {
                            grid.getDataProvider().refreshAll();
                        }
                    }
            );
        });
        // add a child item to the selected item and deep refresh the selected item
        addButton.addClickListener(buttonClickEvent -> {
            grid.getSelectionModel().getFirstSelectedItem().ifPresent(
                    selected -> {
                        Department department = new Department(nextid, "New child "+ nextid, selected,"");
                        nextid++;
                        departmentData.getDepartments().add(department);
                        grid.getDataProvider().refreshItem(selected,true);
                    }
            );
        });
    }

    private TreeGrid<Department> buildGrid() {
        ExplorerTreeGrid<Department> grid = new ExplorerTreeGrid<>();
        HierarchicalDataProvider<Department, Void> dataProvider =
            new AbstractBackEndHierarchicalDataProvider<Department, Void>() {

                @Override
                public int getChildCount(HierarchicalQuery<Department, Void> query) {
                    return departmentData.getChildDepartments(query.getParent()).size();
                }

                @Override
                public boolean hasChildren(Department item) {
                    return departmentData.hasChildren(item);
                }

                @Override
                protected Stream<Department> fetchChildrenFromBackEnd(
                        HierarchicalQuery<Department, Void> query) {
                    return departmentData.getChildDepartments(query.getParent(), query.getOffset()).stream();
                }
            };

        grid.setDataProvider(dataProvider);
        grid.addComponentHierarchyColumn(value -> {
            Span span = new Span();
            if (value.isArchive()) {
                span.add(VaadinIcon.ARCHIVE.create());
            }
            span.add(new Span(value.getName()));
            return span;
        }).setHeader("Department Name");
        grid.setWidthFull();
        grid.expand(departmentData.getRootDepartments());
        return grid;
    }
}
