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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;

import java.util.List;

/**
 * Renders components as hierarchy column for tree grid. Basically puts
 * <code>flow-component-renderer</code> tag inside of
 * <code>explorer-tree-grid-toggle</code>
 *
 * It's a copy of HierarchyColumnComponentRenderer of the Vaadin Grid
 * that changes the tree-toggle component to <code>explorer-tree-grid-toggle</code>
 * and add some attributes to render the connectors
 *
 *
 * @param <COMPONENT>
 *            the type of the output component
 * @param <SOURCE>
 *            the type of the input model object
 */
public class ExplorerTreeHierarchyColumnComponentRenderer<COMPONENT extends Component, SOURCE> extends ComponentRenderer<COMPONENT, SOURCE> {

    public ExplorerTreeHierarchyColumnComponentRenderer(
            ValueProvider<SOURCE, COMPONENT> componentProvider,
            ExplorerTreeGrid<SOURCE> grid) {
        super(componentProvider);

        withFunction("onClick", item -> {
            if (grid.isExpanded(item)) {
                grid.collapse(List.of(item), true);
            } else {
                grid.expand(List.of(item), true);
            }
        });

        withProperty("children",
                item -> grid.getDataCommunicator().hasChildren(item));
    }
    @Override
    protected String getTemplateExpression() {
        // The click listener needs to check if the event gets canceled (by
        // vaadin-grid-tree-toggle) and only invoke the callback if it does.
        // vaadin-grid-tree-toggle will cancel the event if the user clicks on
        // a non-focusable element inside the toggle.
        var clickListener = "e => requestAnimationFrame(() => { e.defaultPrevented && onClick(e) })";

        /* The next line has been customized */
        return "<explorer-tree-grid-toggle @click=${" + clickListener
                + "} class=${item.cssClassName} .leaf=${!item.children} .last=${model.last} .first=${model.first} .parentlines=${model.parentlines} .expanded=${model.expanded} .level=${model.level}>"
                + super.getTemplateExpression() + "</explorer-tree-grid-toggle>";

        /* End of customization */
    }
}
