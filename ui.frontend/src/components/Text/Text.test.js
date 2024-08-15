import { render, screen } from '@testing-library/react';
import React from 'react';
import { ModelManager } from '@adobe/aem-spa-page-model-manager';
import Text from './Text';
import '@testing-library/jest-dom/extend-expect';
import sinon from 'sinon';
import extractModelId from '../../utils/extract-model-id';

describe('Text ->', () => {
  const ROOT_NODE_CLASS_NAME = 'route-node';
  const RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR = '[data-rte-editelement]';
  const CONTENT_PATH = '/content/test/cq/path';
  const TEXT_DATA_CLASS_NAME = 'text-data-selector';
  const TEXT_DATA_STR = 'dummy string text';
  const TEXT_DATA = `<span class="${TEXT_DATA_CLASS_NAME}">${TEXT_DATA_STR}</span>`;

  let rootNode;

  let sandbox = sinon.createSandbox();

  beforeEach(() => {
    sandbox
      .stub(ModelManager, 'getData')
      .withArgs({ pagePath: CONTENT_PATH })
      .resolves({ test: true });

    rootNode = document.createElement('div');
    rootNode.className = ROOT_NODE_CLASS_NAME;
    document.body.appendChild(rootNode);

    ModelManager.initialize();

    expect(document.querySelector('.' + ROOT_NODE_CLASS_NAME)).not.toBeNull();
  });

  afterEach(() => {
    window.location.hash = '';

    if (rootNode) {
      document.body.removeChild(rootNode);
    }

    sandbox.restore();
  });

  it('should render the text component with no parameter', () => {
    expect(rootNode.childElementCount).toEqual(0);
    render(<Text />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(
      screen.queryBySelector(RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR)
    ).toBeNull();
  });

  it('should render the text component that contains the provided text as a string', () => {
    expect(rootNode.childElementCount).toEqual(0);
    render(<Text text={TEXT_DATA} />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(
      screen.queryBySelector(RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR)
    ).toBeNull();
    expect(rootNode.firstChild.innerHTML).toContain(TEXT_DATA_STR);
  });

  it('should render the text component that contains the provided text as a DOM structure', () => {
    expect(rootNode.childElementCount).toEqual(0);
    render(<Text text={TEXT_DATA} richText={true} />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(
      screen.queryBySelector(RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR)
    ).not.toBeNull();
    expect(screen.getByClassName(TEXT_DATA_CLASS_NAME).innerHTML).toBe(
      TEXT_DATA_STR
    );
  });

  it('should render the text as a rich text component', () => {
    expect(rootNode.childElementCount).toEqual(0);
    render(<Text richText={true} />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(
      screen.queryBySelector(RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR)
    ).not.toBeNull();
  });

  it('should render the text as a rich text component with a given id', () => {
    expect(rootNode.childElementCount).toEqual(0);

    render(<Text cqPath={CONTENT_PATH} richText={true} />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(
      screen.queryBySelector(RTE_EDIT_ELEMENT_DATA_ATTR_SELECTOR)
    ).not.toBeNull();
    expect(
      screen.getById(extractModelId(CONTENT_PATH))
    ).not.toBeNull();
  });
});