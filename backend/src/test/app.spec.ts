describe('AppController (e2e)', () => {
  let a, b, c;

  beforeEach(async () => {
      a = 1;
      b = 2;
      c = 3;
  });

  it('/ (GET)', () => {
    expect(a + b).toBe(c);
  });
});
