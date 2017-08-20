import { MyblogPage } from './app.po';

describe('myblog App', () => {
  let page: MyblogPage;

  beforeEach(() => {
    page = new MyblogPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
