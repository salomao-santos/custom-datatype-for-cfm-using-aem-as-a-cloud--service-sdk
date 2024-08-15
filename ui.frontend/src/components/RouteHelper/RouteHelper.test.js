import { render, screen } from '@testing-library/react';
import { BrowserRouter, MemoryRouter, Route } from 'react-router-dom';
import React, { Component } from 'react';
import { ModelManager } from '@adobe/aem-spa-page-model-manager';
import { CompositeComponent, withRoute } from './RouteHelper';
import '@testing-library/jest-dom/extend-expect';
import sinon from 'sinon';

describe('RouterHelper ->', () => {
  const ROUTE_CONTENT_CLASS_NAME = 'route-content';
  const ROOT_NODE_CLASS_NAME = 'route-node';
  const PAGE_TITLE = 'Page Title Test';
  const CUSTOM_ROUTE_PATH = '/content/custom';
  const CUSTOM_ROUTE_PATH_2 = '/content/custom/2';
  const CUSTOM_ROUTE_PATH_ALIAS_2 = '/custom2';

  const PROPS_INLINE_SNAPSHOT = `
<Router
  history={
    Object {
      "action": "POP",
      "block": [Function],
      "createHref": [Function],
      "go": [Function],
      "goBack": [Function],
      "goForward": [Function],
      "length": 1,
      "listen": [Function],
      "location": Object {
        "hash": "",
        "pathname": "/",
        "search": "",
        "state": undefined,
      },
      "push": [Function],
      "replace": [Function],
    }
  }
>
  <CompositeRoute
    cqModel={
      Object {
        "path": "/content/page/path",
        "title": "Page Title Test",
      }
    }
  />
</Router>
`;

  const PATH_AND_PROPS_INLINE_SNAPSHOT = `
<Router
  history={
    Object {
      "action": "POP",
      "block": [Function],
      "createHref": [Function],
      "go": [Function],
      "goBack": [Function],
      "goForward": [Function],
      "length": 1,
      "listen": [Function],
      "location": Object {
        "hash": "",
        "pathname": "/",
        "search": "",
        "state": undefined,
      },
      "push": [Function],
      "replace": [Function],
    }
  }
>
  <CompositeRoute
    cqModel={
      Object {
        "path": "/content/page/path",
        "title": "Page Title Test",
      }
    }
    cqPath="${CUSTOM_ROUTE_PATH}"
  />
</Router>
`;

  let rootNode;

  class RouteContent extends Component {
    render() {
      return (
        <div
          data-title={this.props.cqModel && this.props.cqModel.title}
          className={ROUTE_CONTENT_CLASS_NAME}
        />
      );
    }
  }

  let sandbox = sinon.createSandbox();

  beforeEach(() => {
    sandbox
      .stub(ModelManager, 'getData')
      .withArgs({ pagePath: CUSTOM_ROUTE_PATH })
      .resolves({})
      .withArgs({ pagePath: CUSTOM_ROUTE_PATH_2 })
      .resolves({})
      .withArgs({ pagePath: CUSTOM_ROUTE_PATH_ALIAS_2 })
      .resolves({});

    rootNode = document.createElement('div');
    rootNode.className = ROOT_NODE_CLASS_NAME;
    document.body.appendChild(rootNode);
  });

  afterEach(() => {
    window.location.hash = '';

    if (rootNode) {
      document.body.removeChild(rootNode);
    }

    sandbox.restore();
  });

  describe('withRoute ->', () => {
    it('should render the wrapped component without error', () => {
      const cqModel = {
        path: '/content/page/path',
        title: PAGE_TITLE
      };

      let WrappedComponent = withRoute(RouteContent);
      render(
        <BrowserRouter>
          <WrappedComponent cqModel={cqModel} />
        </BrowserRouter>,
        { container: rootNode }
      );

      expect(
        screen.getByClassName(ROUTE_CONTENT_CLASS_NAME)
      ).toBeInTheDocument();
    });

    it('should render the correct component', () => {
      const { container } = render(<withRoute />);
      const pathMap = Array.from(container.querySelectorAll('Route')).reduce((pathMap, route) => {
        const routeProps = route.props;
        pathMap[routeProps.path] = routeProps.component;
        return pathMap;
      }, {});

      expect(pathMap['/content/page/path']).toBe(CompositeComponent);
    });

    it('should set the extension to the route URL', () => {
      const cqModel = {
        path: '/content/page/path',
        title: PAGE_TITLE
      };

      let WrappedComponent = withRoute(RouteContent, 'extension');

      const { asFragment } = render(
        <BrowserRouter>
          <WrappedComponent cqPath={CUSTOM_ROUTE_PATH} cqModel={cqModel} />
        </BrowserRouter>
      );

      expect(asFragment()).toMatchInlineSnapshot(PATH_AND_PROPS_INLINE_SNAPSHOT);
    });

    it('should render page without extension', () => {
      let WrappedComponent = withRoute(RouteContent);
      render(
        <MemoryRouter initialEntries={[CUSTOM_ROUTE_PATH]}>
          <WrappedComponent cqPath={CUSTOM_ROUTE_PATH} />
        </MemoryRouter>,
        { container: rootNode }
      );

      expect(
        screen.getByClassName(ROUTE_CONTENT_CLASS_NAME)
      ).toBeInTheDocument();
    });

    it('should encapsulate and hide the wrapped component in a route', () => {
      const cqModel = {
        path: '/content/page/path',
        title: PAGE_TITLE
      };

      let WrappedComponent = withRoute(RouteContent);
      render(
        <BrowserRouter>
          <WrappedComponent cqPath={'path/test'} cqModel={cqModel} />
        </BrowserRouter>,
        { container: rootNode }
      );

      expect(screen.queryByClassName(ROUTE_CONTENT_CLASS_NAME)).toBeNull();
    });

    it('should set the correct props in route', () => {
      const cqModel = {
        path: '/content/page/path',
        title: PAGE_TITLE
      };

      const { asFragment } = render(
        <BrowserRouter>
          <WrappedComponent cqModel={cqModel} />
        </BrowserRouter>
      );

      expect(asFragment()).toMatchInlineSnapshot(PROPS_INLINE_SNAPSHOT);
    });

    it('should set the correct path and props in route', () => {
      const cqModel = {
        path: '/content/page/path',
        title: PAGE_TITLE
      };

      const { asFragment } = render(
        <BrowserRouter>
          <WrappedComponent cqPath={CUSTOM_ROUTE_PATH} cqModel={cqModel} />
        </BrowserRouter>
      );

      expect(asFragment()).toMatchInlineSnapshot(PATH_AND_PROPS_INLINE_SNAPSHOT);
    });
  });
});