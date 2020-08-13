package org.vaadin.explorer;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.Route;
import org.vaadin.explorer.bean.Account;
import org.vaadin.explorer.bean.Department;
import org.vaadin.explorer.bean.DepartmentData;
import org.vaadin.explorer.service.AccountService;

import java.util.stream.Stream;

/**
 * @author jcgueriaud
 */
@Route(value = "lazy", layout = MainLayout.class)
public class LazyView extends Div {


    public LazyView() {
        setSizeFull();
        TreeGrid<Account> grid = buildGrid();
        add(grid);
    }

    private TreeGrid<Account> buildGrid() {
        AccountService accountService = new AccountService();
        TreeGrid<Account> grid = new ExplorerTreeGrid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COLUMN_BORDERS);
        grid.addHierarchyColumn(Account::toString).setHeader("Account Title");
        grid.addColumn(Account::getCode).setHeader("Code");
        grid.addColumn(Account::getCode).setHeader("Code");

        HierarchicalDataProvider dataProvider =
                new AbstractBackEndHierarchicalDataProvider<Account, Void>() {

                    @Override
                    public int getChildCount(HierarchicalQuery<Account, Void> query) {
                        return (int) accountService.getChildCount(query.getParent());
                    }

                    @Override
                    public boolean hasChildren(Account item) {
                        return accountService.hasChildren(item);
                    }

                    @Override
                    protected Stream<Account> fetchChildrenFromBackEnd(
                            HierarchicalQuery<Account, Void> query) {
                        return accountService.fetchChildren(query.getParent()).stream();
                    }
                };

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        return grid;
    }
}
