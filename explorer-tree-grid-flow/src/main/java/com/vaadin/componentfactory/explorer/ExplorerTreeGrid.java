package com.vaadin.componentfactory.explorer;

/*
 * #%L
 * explorer-tree-grid-flow
 * %%
 * Copyright (C) 2020 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.AbstractGridMultiSelectionModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A grid component for displaying hierarchical tabular data
 * and displaying connectors between parent and children
 *
 * @param <T>
 *            the grid bean type
 */
@Uses(Icon.class)
@NpmPackage(value="@vaadin/themable-element", version = "0.3.0")
@CssImport("./src/explorer-tree-toggle.js")
@JavaScript("./src/explorer-grid-connector.js")
public class ExplorerTreeGrid<T> extends TreeGrid<T> {

    public ExplorerTreeGrid() {
    }

    public ExplorerTreeGrid(Class<T> beanType) {
        super(beanType);
    }

    public ExplorerTreeGrid(HierarchicalDataProvider<T, ?> dataProvider) {
        super(dataProvider);
    }

    protected void initConnector() {
        super.initConnector();
        (getUI().orElseThrow(() -> new IllegalStateException("Connector can only be initialized for an attached Grid")))
                .getPage().executeJs("window.Vaadin.Flow.explorerGridConnector.initLazy($0)", new Serializable[]{this.getElement()});
    }


    /**
     * To update with prefix icon, suffix icon
     *
     * @param labelProvider
     * @return
     */
    @Override
    public Column<T> addHierarchyColumn(ValueProvider<T, ?> labelProvider) {
        return addHierarchyColumn(labelProvider, object -> "");
    }
    /**
     * To update with prefix icon, suffix icon
     *
     * @param labelProvider
     * @param iconProvider
     * @return
     */
    public Column<T> addHierarchyColumn(ValueProvider<T, ?> labelProvider, ValueProvider<T, ?> iconProvider) {
        Column<T> column = addColumn(TemplateRenderer
                .<T>of("<explorer-tree-grid-toggle "
                        + "leaf='[[item.leaf]]' last='[[last]]' icon='[[item.icon]]' first='[[first]]' parentlines='{{parentlines}}' expanded='{{expanded}}' " +
                        ">[[item.name]]  "
                        + "</explorer-tree-grid-toggle>")
                .withProperty("leaf",
                        item -> {
                            return !getDataCommunicator().hasChildren(item);
                        })
                .withProperty("icon", iconProvider)
                .withProperty("name", labelProvider));

        SerializableComparator<T> comparator = (a, b) -> {
            return compareMaybeComparables(labelProvider.apply(a), labelProvider.apply(b));
        };
        column.setComparator(comparator);
        return column;

    }

    public <V extends Component> Column<T> addComponentHierarchyColumn(
            ValueProvider<T, V> componentProvider) {
        return addColumn(new ExplorerTreeHierarchyColumnComponentRenderer<V, T>(
                componentProvider).withProperty("leaf",
                item -> !getDataCommunicator().hasChildren(item)));
    }

    public GridSelectionModel<T> setHierarchicalSelectionMode(SelectionMode selectionMode) {
        if (SelectionMode.MULTI == selectionMode) {
            GridSelectionModel<T> model = new AbstractGridMultiSelectionModel<T>(this) {
                @Override
                protected void fireSelectionEvent(SelectionEvent<Grid<T>, T> event) {
                    ((ExplorerTreeGrid<T>) this.getGrid()).fireEvent((ComponentEvent<Grid<?>>) event);
                }

                @Override
                public void selectFromClient(T item) {
                    updateSelection(new HashSet<>(getChildrenRecursively(Collections.singletonList(item), 99)),
                        Collections.emptySet());
                }

                @Override
                public void deselectFromClient(T item) {
                    updateSelection(Collections.emptySet(), new HashSet<>(getChildrenRecursively(Collections.singletonList(item), 99)));
                }

            };
            setSelectionModel(model, selectionMode);
            return model;
        } else {
            return super.setSelectionMode(selectionMode);
        }
    }


    protected Collection<T> getChildrenRecursively(Collection<T> items,
                                                            int depth) {
        List<T> itemsWithChildren = new ArrayList<>();
        if (depth < 0) {
            return itemsWithChildren;
        }
        items.stream()
            .forEach(item -> {
                itemsWithChildren.add(item);
                if (getDataCommunicator().hasChildren(item)) {
                    itemsWithChildren.addAll(
                        getChildrenRecursively(getDataProvider()
                            .fetchChildren(
                                new HierarchicalQuery<>(null, item))
                            .collect(Collectors.toList()), depth - 1));
                }
            });
        return itemsWithChildren;
    }
}
