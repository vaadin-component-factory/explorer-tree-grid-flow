/**
@license
Copyright (c) 2017 Vaadin Ltd.
This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
*/
import { PolymerElement } from '@polymer/polymer/polymer-element.js';

import '@polymer/polymer/lib/elements/custom-style.js';
import { Debouncer } from '@polymer/polymer/lib/utils/debounce.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { DirMixin } from '@vaadin/vaadin-element-mixin/vaadin-dir-mixin.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { microTask } from '@polymer/polymer/lib/utils/async.js';
const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<custom-style>
  <style>
    @font-face {
      font-family: "vaadin-grid-tree-icons";
      src: url(data:application/font-woff;charset=utf-8;base64,d09GRgABAAAAAAQkAA0AAAAABrwAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAABGRlRNAAAECAAAABoAAAAcgHwa6EdERUYAAAPsAAAAHAAAAB4AJwAOT1MvMgAAAZQAAAA/AAAAYA8TBIJjbWFwAAAB8AAAAFUAAAFeGJvXWmdhc3AAAAPkAAAACAAAAAgAAAAQZ2x5ZgAAAlwAAABLAAAAhIrPOhFoZWFkAAABMAAAACsAAAA2DsJI02hoZWEAAAFcAAAAHQAAACQHAgPHaG10eAAAAdQAAAAZAAAAHAxVAgBsb2NhAAACSAAAABIAAAASAIAAVG1heHAAAAF8AAAAGAAAACAACgAFbmFtZQAAAqgAAAECAAACTwflzbdwb3N0AAADrAAAADYAAABZQ7Ajh3icY2BkYGAA4twv3Vfi+W2+MnCzMIDANSOmbGSa2YEZRHEwMIEoAAoiB6sAeJxjYGRgYD7w/wADAwsDCDA7MDAyoAI2AFEEAtIAAAB4nGNgZGBg4GBgZgDRDAxMDGgAAAGbABB4nGNgZp7JOIGBlYGBaSbTGQYGhn4IzfiawZiRkwEVMAqgCTA4MDA+38d84P8BBgdmIAapQZJVYGAEAGc/C54AeJxjYYAAxlAIzQTELAwMBxgZGB0ACy0BYwAAAHicY2BgYGaAYBkGRgYQiADyGMF8FgYbIM3FwMHABISMDArP9/3/+/8/WJXC8z0Q9v8nEp5gHVwMMMAIMo+RDYiZoQJMQIKJARUA7WBhGN4AACFKDtoAAAAAAAAAAAgACAAQABgAJgA0AEIAAHichYvBEYBADAKBVHBjBT4swl9KS2k05o0XHd/yW1hAfBFwCv9sIlJu3nZaNS3PXAaXXHI8Lge7DlzF7C1RgXc7xkK6+gvcD2URmQB4nK2RQWoCMRiFX3RUqtCli65yADModOMBLLgQSqHddRFnQghIAnEUvEA3vUUP0LP0Fj1G+yb8R5iEhO9/ef/7FwFwj28o9EthiVp4hBlehcfUP4Ur8o/wBAv8CU+xVFvhOR7UB7tUdUdlVRJ6HnHWTnhM/V24In8JT5j/KzzFSi2E53hUz7jCcrcIiDDwyKSW1JEct2HdIPH1DFytbUM0PofWdNk5E5oUqb/Q6HHBiVGZpfOXkyUMEj5IyBuNmYZQjBobfsuassvnkKLe1OuBBj0VQ8cRni2xjLWsHaM0jrjx3peYA0/vrdmUYqe9iy7bzrX6eNP7Jh1SijX+AaUVbB8AAHicY2BiwA84GBgYmRiYGJkZmBlZGFkZ2djScyoLMgzZS/MyDQwMwLSruZMzlHaB0q4A76kLlwAAAAEAAf//AA94nGNgZGBg4AFiMSBmYmAEQnYgZgHzGAAD6wA2eJxjYGBgZACCKxJigiD6mhFTNowGACmcA/8AAA==) format('woff');
      font-weight: normal;
      font-style: normal;
    }
  </style>
</custom-style>`;

document.head.appendChild($_documentContainer.content);
/**
 * `<vaadin-grid-tree-toggle>` is a helper element for the `<vaadin-grid>`
 * that provides toggle and level display functionality for the item tree.
 *
 * #### Example:
 * ```html
 * <vaadin-grid-column>
 *   <template class="header">Package name</template>
 *   <template>
 *     <vaadin-grid-tree-toggle
 *         leaf="[[!item.hasChildren]]"
 *         expanded="{{expanded}}"
 *         level="[[level]]">
 *       [[item.name]]
 *     </vaadin-grid-tree-toggle>
 *   </template>
 * </vaadin-grid-column>
 * ```
 *
 * ### Styling
 *
 * The following shadow DOM parts are available for styling:
 *
 * Part name | Description
 * ---|---
 * `toggle` | The tree toggle icon
 *
 * The following state attributes are available for styling:
 *
 * Attribute    | Description | Part name
 * ---|---|---
 * `expanded` | When present, the toggle is expanded | :host
 * `leaf` | When present, the toggle is not expandable, i. e., the current item is a leaf | :host
 *
 * The following custom CSS properties are available on
 * the `<vaadin-grid-tree-toggle>` element:
 *
 * Custom CSS property | Description | Default
 * ---|---|---
 * `--vaadin-grid-tree-toggle-level-offset` | Visual offset step for each tree sublevel | `1em`
 *
 * @extends PolymerElement
 * @mixes ThemableMixin
 */
class ExplorerTreeToggleElement extends ThemableMixin(DirMixin(PolymerElement)) {
  static get template() {
    return html`
    <style>
    :host {
        --vaadin-grid-tree-toggle-level-offset: 2em;
        align-items: center;
        vertical-align: middle;
        margin-left: calc(var(--lumo-space-s) * -1);
        -webkit-tap-highlight-color: transparent;
      }

      :host(:not([leaf])) {
        cursor: default;
      }

      [part="toggle"] {
        display: inline-block;
        font-size: 1em;
        line-height: 1;
        width: 1.5em;
        height: 1em;
        text-align: center;
        color: var(--lumo-contrast-50pct);
        /* Increase touch target area */
        margin-top: calc(1em / -3);
        /* padding: calc(1em / 3);
        margin: calc(1em / -3);*/
      }

      :host(:not([dir="rtl"])) [part="toggle"] {
        margin-right: 0;
      }

      @media (hover: hover) {
        :host(:hover) [part="toggle"] {
          color: var(--lumo-contrast-80pct);
        }
      }

      [part="toggle"]::before {
        font-family: "lumo-icons";
        display: inline-block;
        height: 100%;
      }
/*
      :host(:not([expanded])) [part="toggle"]::before {
        content: var(--lumo-icons-angle-right);
      }

      :host([expanded]) [part="toggle"]::before {
        content: var(--lumo-icons-angle-right);
        transform: rotate(90deg);
      }
*/
      /* RTL specific styles */

      :host([dir="rtl"]) {
        margin-left: 0;
        margin-right: calc(var(--lumo-space-s) * -1);
      }

      :host([dir="rtl"]) [part="toggle"] {
        margin-left: 0;
      }

      :host([dir="rtl"][expanded]) [part="toggle"]::before {
        transform: rotate(-90deg);
      }

      :host([dir="rtl"]:not([expanded])) [part="toggle"]::before,
      :host([dir="rtl"][expanded]) [part="toggle"]::before {
        content: var(--lumo-icons-angle-left);
      }
      
      :host {
        display: inline-flex;
        align-items: baseline;

        /* CSS API for :host */
        --vaadin-grid-tree-toggle-level-offset: 2em;
        /*
          ShadyCSS seems to polyfill :dir(rtl) only for :host, thus using
          a host custom CSS property for ltr/rtl toggle icon choice.
         */
        ---collapsed-icon: "\\e7be\\00a0";
      }

      :host(:dir(rtl)) {
        ---collapsed-icon: "\\e7bd\\00a0";
      }

      :host([hidden]) {
        display: none !important;
      }

      :host(:not([leaf])) {
        cursor: pointer;
      }

      [part="toggle"] {
        flex: none;
      }

      [part="toggle"]::before {
        line-height: 1em; /* make icon font metrics not affect baseline */
      }

      :host(:not([expanded])) [part="toggle"]::before {
        content: var(---collapsed-icon);
      }

      :host([expanded]) [part="toggle"]::before {
        content: "\\e7bc\\00a0"; /* icon glyph + single non-breaking space */
      }

      :host([leaf]) [part="toggle"] {
        visibility: hidden;
      }
      
      .level-spacer {
        flex: none;
        display: inline-block;
        width: var(--vaadin-grid-tree-toggle-level-offset);
      }
      
      .level-spacer-hidden {
        visibility: hidden;
      }
      
      /* Experimental support for hierarchy connectors, using an unsupported selector */
      :host([theme~="connectors"]) .level-spacer {
        position: relative;
        font-size: 1em;
        height: 1em /*calc( 1em + 4px );*/
      }

      :host([theme~="connectors"]) .level-spacer::before {
        display: block;
        content: "";
        margin-top: calc(var(--lumo-space-m) * -1);
        height: calc(var(--lumo-space-m) + 3em);
        border-left: 1px var(--border-style) var(--color-border);
        margin-left: calc(var(--vaadin-grid-tree-toggle-level-offset) / 2 - 4px);
      }
  
      :host([theme~="connectors"]) .toggle {
        position: relative;
        font-size: 1em;
        height: 1.75em;
      }
      :host([theme~="connectors"]) .toggle-hori-line {
        position: absolute;
        right: 0;
        height:1px;
        width: calc(50% + 4px);
        border-top: 1px var(--border-style) var(--color-border);
        margin-top: 0.75em;
      }
      :host([theme~="connectors"]) .toggle-top-line {
        position: absolute;
        margin-top: calc(var(--lumo-space-m) * -1);
        height: calc(var(--lumo-space-m) + 0.9em - 1px);
        width: 1px;
        margin-left: calc(var(--vaadin-grid-tree-toggle-level-offset) / 2 - 4px);
        border-left: 1px var(--border-style) var(--color-border);
      }
      :host([theme~="connectors"]) .toggle-bottom-line {
        position: absolute;
        bottom: 0;
        height: calc(var(--lumo-space-m) + 0.9em + 1px);
        margin-bottom: calc(var(--lumo-space-m) * -1);
        width: 1px;
        margin-left: calc(var(--vaadin-grid-tree-toggle-level-offset) / 2 - 4px);
        border-left: 1px var(--border-style) var(--color-border);
      }
      :host([theme~="connectors"][last]) .toggle-bottom-line {
        visibility: hidden;
      }
      
      :host([theme~="connectors"][first]) .toggle-top-line {
        visibility: hidden;
      }
      
      [part="toggle"]::before {
        background: white;
        transform: rotate(0deg);
      }
      
      [part="toggle"] {
        padding-right: 8px;
      }
      
     /* .icon {
        --iron-icon-height: 1em;
        --iron-icon-width: 1em;
        width: 24px;
      }*/
      
    </style>
    <template is="dom-repeat" items="{{parentlines}}">
      <template is="dom-if" if="{{item}}">
      <span class="level-spacer"> </span>
      </template>
      <template is="dom-if" if="{{!item}}">
      <span class="level-spacer level-spacer-hidden"></span>
      </template>
    </template>
    <span class="toggle">
      <span class="toggle-top-line"></span>
      <span class="toggle-bottom-line"></span>
      <span class="toggle-hori-line"></span>
      <span part="toggle"></span>
    </span>
    <slot></slot>
`;
  }

  static get is() {
    return 'explorer-tree-grid-toggle';
  }

  static get properties() {
    return {
      /**
       * Current level of the tree represented with a horizontal offset
       * of the toggle button.
       */
      level: {
        type: Number,
        value: 0,
        observer: '_levelChanged'
      },
      parentlines: {
        type: Array,
        value: [5,6]
      },
      /**
       * Hides the toggle icon and disables toggling a tree sublevel.
       */
      leaf: {
        type: Boolean,
        value: false,
        reflectToAttribute: true
      },

      /**
       *
       */
      last: {
        type: Boolean,
        value: false,
        reflectToAttribute: true
      },
      first: {
        type: Boolean,
        value: false,
        reflectToAttribute: true
      },
      /**
       * Sublevel toggle state.
       */
      expanded: {
        type: Boolean,
        value: false,
        reflectToAttribute: true,
        notify: true
      }
    };
  }

  ready() {
    super.ready();
    this.addEventListener('click', e => this._onClick(e));
  }
/*
  _updateTest() {
    //debugger;
    if (this.parentElement) {
      //debugger;
      if (this.parentElement.nextElementSibling != null) {
        const nextChild = this.parentElement.nextElementSibling.firstElementChild;
        if (nextChild != null) {
          const nextLevel = nextChild.style["---level"];
          if (this.level == nextLevel) {
            console.log("next element same level");
          } else if (this.level > nextLevel) {
            console.log("next element PARENT level");
          } else {
            console.log("next element CHILD level");
          }
        }
      } else {
        console.log("next element NULL -  level");
      }
      if (this.parentElement.previousElementSibling != null) {
        const previousChild = this.parentElement.previousElementSibling.firstElementChild;
        if (previousChild != null) {
          const previousLevel = previousChild.style["---level"];
          if (this.level == previousLevel) {
            console.log("previous element same level");
          } else if (this.level > previousLevel) {
            console.log("previous element PARENT level");
          } else {
            previousChild.style.color = "red";
            console.log("previous element CHILD level");
          }
        } else {
          this.style.color = "purple";
        }
      } else {
        console.log("previous element NULL -  level");
        this.style.color = "blue";
      }
    }
  }*/
  /*
  connectedCallback() {
    super.connectedCallback();
    console.log("PARENT "+this.parentElement.tagName + '.' + this.parentElement.className);
    console.log("PARENT "+this.parentNode.tagName + '.' + this.parentNode.className);
  }
*/
  _onClick(e) {
    if (this.leaf) {
      return;
    }

    e.preventDefault();
    this.expanded = !this.expanded;
  }

  _levelChanged(level) {
    const value = Number(level).toString();
    this.style['---level'] = value;
    // Async is to make DOM updates applied before evaluating the style
    // update, required for polyfilled RTL support in MSIE and Edge.
    this._debouncerUpdateLevel = Debouncer.debounce(
      this._debouncerUpdateLevel,
      microTask,
      () => this.updateStyles({'---level': value})
    );
  }
}

customElements.define(ExplorerTreeToggleElement.is, ExplorerTreeToggleElement);

export { ExplorerTreeToggleElement };
