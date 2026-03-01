import { test, expect } from '@playwright/test';

test.describe('Asturian Cinema', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/');
  });

  test('homepage loads correctly', async ({ page }) => {
    await expect(page).toHaveTitle(/Asturian Cinema/);
    await expect(page.locator('.logo-text')).toContainText('AsturianCinema');
  });

  test('navigation to catalog', async ({ page }) => {
    await page.click('text=Catálogo');
    await expect(page.url()).toContain('/catalog');
  });

  test('navigation to blog', async ({ page }) => {
    await page.click('text=Blog');
    await expect(page.url()).toContain('/blog');
  });

  test('login page loads', async ({ page }) => {
    await page.click('text=Iniciar Sesión');
    await expect(page.url()).toContain('/login');
    await expect(page.locator('h1')).toContainText('Iniciar Sesión');
  });

  test('register page loads', async ({ page }) => {
    await page.click('text=Iniciar Sesión');
    await page.click('text=Regístrate');
    await expect(page.url()).toContain('/register');
    await expect(page.locator('h1')).toContainText('Crear Cuenta');
  });

  test('theme toggle works', async ({ page }) => {
    const themeToggle = page.locator('.theme-toggle');
    await themeToggle.click();
    await page.waitForTimeout(500);
    await themeToggle.click();
  });
});

test.describe('Catalog Page', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/catalog');
  });

  test('catalog page loads', async ({ page }) => {
    await expect(page.locator('h1')).toContainText('Catálogo');
  });
});

test.describe('Movie Detail', () => {
  test('movie detail page loads', async ({ page }) => {
    await page.goto('/movie/1');
    await expect(page.locator('h1')).toBeVisible();
  });
});
