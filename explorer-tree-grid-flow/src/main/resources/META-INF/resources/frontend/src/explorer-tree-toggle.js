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

class ExplorerTreeToggleElement extends ThemableMixin(DirMixin(PolymerElement)) {
  static get template() {
    return html`
    <style>
    /*
     
*/
    :host {
        --explorer-tree-grid-toggle-level-offset: 2em;
        --explorer-tree-grid-icon-type-width: 18px;
        --explorer-tree-grid-expand-icon-width: 10px;
        --explorer-tree-grid-icon-type-margin: 2px;
        --explorer-tree-grid-line-color: var(--lumo-contrast-50pct);
        --explorer-tree-grid-icon-color: var(--lumo-contrast-50pct);
        --explorer-tree-grid-border-style: solid;
        display: inline-flex;
        align-items: center;
        margin-left: calc(var(--lumo-space-s) * -1);
        -webkit-tap-highlight-color: transparent;
      }
      
      :host(:not([leaf])) {
        cursor: default;
      }

      [part="toggle"] {
        display: inline-flex;
        justify-content: center;
        font-size: var(--explorer-tree-grid-expand-icon-width);
        line-height: 1;
        width: var(--explorer-tree-grid-icon-type-width);
        height: 1em;
        text-align: center;
        color: var(--explorer-tree-grid-icon-color);
        /* Increase touch target area */
        margin-top: calc(1em / -3);
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
/*
      :host([dir="rtl"]:not([expanded])) [part="toggle"]::before,
      :host([dir="rtl"][expanded]) [part="toggle"]::before {
        content: var(--lumo-icons-angle-left);
      }*/
      
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

      :host([leaf]) [part="toggle"] {
        visibility: hidden;
      }
      
      .level-spacer {
        flex: none;
        display: inline-block;
        width: var(--explorer-tree-grid-toggle-level-offset);
      }
      
      .level-spacer-hidden {
        visibility: hidden;
      }
      
      /* Experimental support for hierarchy connectors, using an unsupported selector */
      :host .level-spacer {
        position: relative;
        font-size: 1em;
        height: 1em /*calc( 1em + 4px );*/
      }

      :host .level-spacer::before {
        display: block;
        content: "";
        margin-top: calc(var(--lumo-space-m) * -1);
        height: calc(var(--lumo-space-m) + 3em);
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
        /* margin-left: calc(var(--explorer-tree-grid-toggle-level-offset) / 2 - 4px);*/
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
      }
  
      :host([dir="rtl"]) .level-spacer::before {
        border-left: 0;
        margin-left: 0;
        border-right: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
        /* margin-left: calc(var(--explorer-tree-grid-toggle-level-offset) / 2 - 4px);*/
        margin-right: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
      }
      :host .toggle {
        position: relative;
        font-size: 1em;
        height: 1.75em;
        width: var(--explorer-tree-grid-toggle-level-offset);
      }
      :host .toggle-hori-line {
        position: absolute;
        right: 0;
        height:1px;
        width: calc(100% - var(--explorer-tree-grid-icon-type-width) / 2 );
        border-top: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
        margin-top: 0.8em;
      }
      :host([dir="rtl"]) .toggle-hori-line {
        left: 0;
      }
      :host .toggle-top-line {
        position: absolute;
        margin-top: calc(var(--lumo-space-m) * -1);
        height: calc(var(--lumo-space-m) + 0.9em - 1px);
        width: 1px;
        /* margin-left: calc(var(--explorer-tree-grid-toggle-level-offset) / 2 - 4px);*/
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      
      :host .toggle-bottom-line {
        position: absolute;
        bottom: 0;
        height: calc(var(--lumo-space-m) + 0.9em + 1px);
        margin-bottom: calc(var(--lumo-space-m) * -1);
        width: 1px;
        /*margin-left: calc(var(--explorer-tree-grid-toggle-level-offset) / 2 - 4px);*/
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      
      :host([dir="rtl"]) .toggle-top-line,
      :host([dir="rtl"]) .toggle-bottom-line,
       {
        border-left: 0;
        margin-left: 0;
        margin-right: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
        border-right: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      
      :host([last]) .toggle-bottom-line {
        visibility: hidden;
      }
      
      :host([first]) .toggle-top-line {
        visibility: hidden;
      }
      
      [part="toggle"]::before {
          background: white;
          transform: rotate(0deg);
          border: 1px solid var(--explorer-tree-grid-icon-color);
      }
      
      :host([expanded]) [part="toggle"]::before {
          content: var(--lumo-icons-minus);
      }
      
      :host(:not([expanded])) [part="toggle"]::before {
          content: var(--lumo-icons-plus);
      }
      
      :host([leaf]) [part="toggle"] {
          visibility: visible;
          content: "";
      }
      
      :host([leaf]) [part="toggle"]::before {
          content: "";
          border: 0;
      }
    
    .icon-type {
        color: var(--explorer-tree-grid-icon-color);
        margin-left: var(--explorer-tree-grid-icon-type-margin);
        margin-right: var(--explorer-tree-grid-icon-type-margin);
       --iron-icon-height: calc( var(--explorer-tree-grid-icon-type-width) - var(--explorer-tree-grid-icon-type-margin) );
       --iron-icon-width: calc( var(--explorer-tree-grid-icon-type-width) - var(--explorer-tree-grid-icon-type-margin) );
    }
      
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
    <template is="dom-if" if="[[icon]]">
        <iron-icon class="icon-type" icon='[[icon]]'></iron-icon>
    </template>
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

      /**
       *
       */
      icon: {
        type: String,
        value: "",
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
