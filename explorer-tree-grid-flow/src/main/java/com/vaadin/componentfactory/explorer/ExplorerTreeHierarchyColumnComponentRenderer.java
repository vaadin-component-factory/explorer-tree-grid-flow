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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataGenerator;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.data.renderer.ComponentDataGenerator;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Rendering;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.internal.JsonSerializer;
import elemental.json.JsonObject;

import java.util.Optional;

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
public class ExplorerTreeHierarchyColumnComponentRenderer<COMPONENT extends Component, SOURCE>
        extends ComponentRenderer<COMPONENT, SOURCE> {

    public ExplorerTreeHierarchyColumnComponentRenderer(
            ValueProvider<SOURCE, COMPONENT> componentProvider) {
        super(componentProvider);
    }

    public ExplorerTreeHierarchyColumnComponentRenderer<COMPONENT, SOURCE> withProperty(
            String property, ValueProvider<SOURCE, ?> provider) {
        setProperty(property, provider);
        return this;
    }

    @Override
    public Rendering<SOURCE> render(Element container,
            DataKeyMapper<SOURCE> keyMapper, Element contentTemplate) {

        ComponentRendering rendering = new ComponentRendering(
                keyMapper == null ? null : keyMapper::key);
        rendering.setTemplateElement(contentTemplate);

        container.getNode().runWhenAttached(ui -> setupTemplateWhenAttached(ui,
                container, rendering, keyMapper));
        return rendering;
    }

    private void setupTemplateWhenAttached(UI ui, Element owner,
            ComponentRendering rendering, DataKeyMapper<SOURCE> keyMapper) {
        String appId = ui.getInternals().getAppId();
        Element templateElement = rendering.getTemplateElement();
        owner.appendChild(templateElement);

        Element container = new Element("div");
        owner.appendVirtualChild(container);
        rendering.setContainer(container);
        String templateInnerHtml;

        if (keyMapper != null) {
            String nodeIdPropertyName = "_renderer_"
                    + templateElement.getNode().getId();

            templateInnerHtml = String.format(
                    "<flow-component-renderer appid=\"%s\" nodeid=\"[[item.%s]]\"></flow-component-renderer>",
                    appId, nodeIdPropertyName);
            rendering.setNodeIdPropertyName(nodeIdPropertyName);
        } else {
            COMPONENT component = createComponent(null);
            if (component != null) {
                container.appendChild(component.getElement());

                templateInnerHtml = String.format(
                        "<flow-component-renderer appid=\"%s\" nodeid=\"%s\"></flow-component-renderer>",
                        appId, component.getElement().getNode().getId());
            } else {
                templateInnerHtml = "";
            }
        }
        /* The next line has been customized */
        templateInnerHtml = "<explorer-tree-grid-toggle  class$='[[item.cssClassName]]' "
                + "leaf='[[item.leaf]]' last='[[last]]' first='[[first]]' parentlines='{{parentlines}}' expanded='{{expanded}}' " +
                ">" + templateInnerHtml + "</explorer-tree-grid-toggle>";
        /* End of customization */
        templateElement.setProperty("innerHTML", templateInnerHtml);
    }

    private class ComponentRendering extends ComponentDataGenerator<SOURCE>
            implements Rendering<SOURCE> {

        private Element templateElement;

        public ComponentRendering(ValueProvider<SOURCE, String> keyMapper) {
            super(ExplorerTreeHierarchyColumnComponentRenderer.this, keyMapper);
        }

        public void setTemplateElement(Element templateElement) {
            this.templateElement = templateElement;
        }

        @Override
        public Element getTemplateElement() {
            return templateElement;
        }

        @Override
        public Optional<DataGenerator<SOURCE>> getDataGenerator() {
            return Optional.of(this);
        }

        @Override
        public void generateData(SOURCE item, JsonObject jsonObject) {
            super.generateData(item, jsonObject);
            // in order to add item.leaf property
            getValueProviders().forEach((key, provider) -> jsonObject.put(key,
                    JsonSerializer.toJson(provider.apply(item))));
        }
    }
}
