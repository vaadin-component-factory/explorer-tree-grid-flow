package com.vaadin.componentfactory.explorer;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport(value="./src/custom-explorer-tree-grid.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        final DrawerToggle drawerToggle = new DrawerToggle();
        final RouterLink simple = new RouterLink("Exp. Tree - Basic", SimpleView.class);
        final RouterLink simpleGridView = new RouterLink("Vaadin Tree - Basic", SimpleGridView.class);
        final RouterLink explorerView = new RouterLink("Exp. Tree - Basic with text and icon", ExplorerView.class);
        final RouterLink lazyTreeView = new RouterLink("Exp. Tree - 100 levels", LazyTreeView.class);
        final RouterLink lazyTreeGridView = new RouterLink("Vaadin Tree - 100 levels", LazyTreeGridView.class);
        final RouterLink componentRendererView = new RouterLink("Exp. Tree - Components", LazyTreeComponentView.class);
        final RouterLink vcomponentRendererView = new RouterLink("Vaadin Tree - Components", LazyTreeGridComponentView.class);
        final RouterLink compactThemeRendererView = new RouterLink("Exp. Tree - Compact Theme", CompactThemeRendererView.class);
        final RouterLink darkThemeRendererView = new RouterLink("Exp. Tree - Dark Theme", DarkThemeRendererView.class);
        final RouterLink customThemeRendererView = new RouterLink("Exp. Tree - Custom Theme", CustomThemeRendererView.class);
        final RouterLink updateGridView = new RouterLink("Exp. Tree - Update example", UpdateGridView.class);
        final VerticalLayout menuLayout = new VerticalLayout(simple,
                simpleGridView, explorerView, lazyTreeView, lazyTreeGridView,
                componentRendererView, vcomponentRendererView,
                compactThemeRendererView, darkThemeRendererView, customThemeRendererView,
                updateGridView);
        addToDrawer(menuLayout);
        addToNavbar(drawerToggle);
    }

}