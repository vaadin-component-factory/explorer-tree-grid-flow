package org.vaadin.explorer;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

/**
 * @author jcgueriaud
 */
public class MainLayout extends AppLayout {

    public MainLayout() {
        final DrawerToggle drawerToggle = new DrawerToggle();
        final RouterLink simple = new RouterLink("Simple", SimpleView.class);
        final RouterLink lazy = new RouterLink("Lazy", LazyView.class);
        final RouterLink simpleGridView = new RouterLink("SimpleGridView", SimpleGridView.class);
        final RouterLink explorerView = new RouterLink("ExplorerView", ExplorerView.class);
        final RouterLink lazyTreeView = new RouterLink("LazyTreeView", LazyTreeView.class);
        final RouterLink componentRendererView = new RouterLink("ComponentRendererView", ComponentRendererView.class);
        final RouterLink compactThemeRendererView = new RouterLink("CompactThemeRendererView", CompactThemeRendererView.class);
        final RouterLink lazyTreeComponentView = new RouterLink("LazyTreeComponentView", LazyTreeComponentView.class);
        final VerticalLayout menuLayout = new VerticalLayout(simple, lazy,
                simpleGridView, explorerView, lazyTreeView, componentRendererView,
                compactThemeRendererView, lazyTreeComponentView);
        addToDrawer(menuLayout);
        addToNavbar(drawerToggle);
    }

}