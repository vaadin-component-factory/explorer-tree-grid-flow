import { css, html, LitElement } from 'lit';
import '@polymer/polymer/lib/elements/custom-style.js';
import { DirMixin } from '@vaadin/component-base/src/dir-mixin.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
/**
 *
 * This is a copy of the <code>vaadin-grid-tree-toggle</code> web component
 * It has been converted to Lit element for performance reasons
 * (the loop introduced in the component is really slow in polymer)
 * The styles and inner HTML are different.
 * The logic is the same.
 */
export default class ExplorerTreeToggleElement extends DirMixin(ThemableMixin(LitElement)) {


  static get properties() {
    return {
      /**
       * Array of boolean that contains all the information of the parents
       * - true if the parent is not the last child (and display a vertical line to its next sibling)
       * - false if the parent is the last child (and hide the line)
       */
      parentlines: { type: Array },
      /**
       * Hides the toggle icon and disables toggling a tree sublevel.
       */
      leaf: {
        type: Boolean,
        reflect: true
      },

      /**
       * true if the item is the last item of its level
       */
      last: {
        type: Boolean,
        reflect: true
      },

      /**
       * Icon to show
       */
      icon: {
        type: String,
        reflect: true
      },

      /**
       * true if the item is the first item of the grid
       */
      first: {
        type: Boolean,
        reflect: true
      },
      /**
       * Sublevel toggle state.
       */
      expanded: {
        type: Boolean,
        reflect: true
      }
    };
  }

  constructor() {
    super();
    this.parentlines = [];
    this.expanded = false;
    this.icon = "";
  }

  static get styles() {
    return css`
        
    :host {
        --explorer-tree-grid-toggle-level-offset: 1.5rem;
        --explorer-tree-grid-icon-type-width: 1.1rem;
        --explorer-tree-grid-expand-icon-width: 0.8rem;
        --explorer-tree-grid-icon-type-margin: 0.1rem;
        --explorer-tree-grid-line-color: var(--lumo-contrast-50pct);
        --explorer-tree-grid-icon-color: var(--lumo-contrast-50pct);
        --explorer-tree-grid-icon-hover-color: var(--lumo-contrast-80pct);
        --explorer-tree-grid-border-style: dotted;
        --explorer-tree-grid-left-offset: var(--lumo-space-s);
        --explorer-tree-grid-default-margin: var(--lumo-space-m);
        --explorer-tree-grid-icon-background: var(--lumo-base-color);
        display: inline-flex;
        align-items: center;
        margin-left: calc(var(--explorer-tree-grid-left-offset) * -1);
        -webkit-tap-highlight-color: transparent;
      }
      
      :host(:not([leaf])) {
        cursor: default;
      }

      [part="toggle"] {
          display: inline-flex;
          justify-content: center;
          font-size: var(--explorer-tree-grid-expand-icon-width);
          width: var(--explorer-tree-grid-icon-type-width);
          min-height: 100%;
          height:unset;
          align-items: center;
          color: var(--explorer-tree-grid-icon-color);
      }

      :host(:not([dir="rtl"])) [part="toggle"] {
        margin-right: 0;
      }

      @media (hover: hover) {
        :host(:hover) [part="toggle"] {
          color: var(--explorer-tree-grid-icon-hover-color);
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
        margin-right: calc(var(--explorer-tree-grid-left-offset) * -1);
      }

      :host([dir="rtl"]) [part="toggle"] {
        margin-left: 0;
      }
      
      :host([dir="rtl"]) .level-spacer::before {
        border-left: 0;
        margin-left: 0;
        border-right: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
        margin-right: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
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
      
      :host .level-spacer {
        position: relative;
        font-size: 1em;
        height: 1em;
      }

      :host .level-spacer::before {
        display: block;
        content: "";
        margin-top: calc(var(--explorer-tree-grid-default-margin) * -1);
        height: calc(var(--explorer-tree-grid-default-margin) + 3em);
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
      }
  
      :host .toggle {
        position: relative;
        font-size: 1em;
        height: 1.75em;
        width: var(--explorer-tree-grid-toggle-level-offset);
      }
      :host .toggle-hori-line {
        position: absolute;
        top: 50%;
        height:1px;
        width: calc(100% - var(--explorer-tree-grid-icon-type-width) / 2 );
        border-top: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      :host(:not([dir="rtl"])) .toggle-hori-line {
        right: 0;
      }
      :host([dir="rtl"]) .toggle-hori-line {
        left: 0;
      }
      :host .toggle-top-line {
        position: absolute;
        margin-top: calc(var(--explorer-tree-grid-default-margin) * -1);
        height: calc(var(--explorer-tree-grid-default-margin) + 50%);
        width: 1px;
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      
      :host .toggle-bottom-line {
        position: absolute;
        bottom: 0;
        height: calc(var(--explorer-tree-grid-default-margin) + 50% - 1px);
        margin-bottom: calc(var(--explorer-tree-grid-default-margin) * -1);
        width: 1px;
        margin-left: calc( var(--explorer-tree-grid-icon-type-width) / 2 );
        border-left: 1px var(--explorer-tree-grid-border-style) var(--explorer-tree-grid-line-color);
      }
      
      :host .toggle-bottom-line {
          height: calc(var(--explorer-tree-grid-default-margin) + 50% - 1px);
      }
      
      :host([dir="rtl"]) .toggle-top-line,
      :host([dir="rtl"]) .toggle-bottom-line {
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
        background: var(--explorer-tree-grid-icon-background);
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
       --lumo-icon-size-m: var(--iron-icon-height);
       --lumo-icon-size-m: var(--iron-icon-width);
      }
      
	  `;
  }
  render() {
    return html`
      ${this.parentlines.map(item =>
          item ? html`<span class="level-spacer"> </span>` : html`<span class="level-spacer level-spacer-hidden"> </span>`
      )}
      <span class="toggle">
      <span class="toggle-top-line"></span>
      <span class="toggle-bottom-line"></span>
      <span class="toggle-hori-line"></span>
      <span part="toggle"></span>
    </span>
      ${(this.icon !== "")?
          html`<vaadin-icon class="icon-type" icon='${this.icon}'></vaadin-icon>`:
          ``}
      <slot></slot>`;
  }

  static get is() {
    return 'explorer-tree-grid-toggle';
  }

  async firstUpdated() {
    this.addEventListener('click', this._handleClick);
  }

  _handleClick(e) {
    if (this.leaf) {
      return;
    }

    e.preventDefault();
    this.expanded = !this.expanded;
    const event = new CustomEvent('expanded-changed', { bubbles: true, composed: true });
    this.dispatchEvent(event);
  }

}

customElements.define(ExplorerTreeToggleElement.is, ExplorerTreeToggleElement);

export { ExplorerTreeToggleElement };
