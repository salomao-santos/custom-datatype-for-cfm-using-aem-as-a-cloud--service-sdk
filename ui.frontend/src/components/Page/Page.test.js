import { render, screen } from '@testing-library/react';
import React from 'react';
import { ModelManager } from '@adobe/aem-spa-page-model-manager';
import Page from './Page';
import '@testing-library/jest-dom/extend-expect';
import sinon from 'sinon';

describe('Page ->', () => {
  const ROOT_NODE_CLASS_NAME = 'route-node';
  const PAGE_CLASS_NAME = 'page';

  let rootNode;

  let sandbox = sinon.createSandbox();

  beforeEach(() => {
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

  it('should render the page component with no parameter', () => {
    expect(rootNode.childElementCount).toEqual(0);
    render(<Page />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(screen.getByClassName(PAGE_CLASS_NAME)).toBeInTheDocument();
  });

  it('should render the page component with extra class names', () => {
    const EXTRA_CLASS_NAMES = 'test-class-names';

    expect(rootNode.childElementCount).toEqual(0);

    render(<Page cssClassNames={EXTRA_CLASS_NAMES} />, { container: rootNode });

    expect(rootNode.childElementCount).toEqual(1);

    expect(screen.getByClassName(EXTRA_CLASS_NAMES)).toBeInTheDocument();
  });
});