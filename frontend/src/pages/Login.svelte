<script>
  import { isAuthenticated } from '../store/auth';
  let username = '';
  let password = '';
  let errorMessage = '';

  async function handleSubmit(event) {
    event.preventDefault();
    errorMessage = '';

    try {
      const response = await fetch('http://localhost:3000/api/v1/auth/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        throw new Error('Login failed');
      }

      const data = await response.json();
      isAuthenticated.set(true);      

      localStorage.setItem('token', data.accessToken);
    } catch (error) {
      errorMessage = error.message;
    }
  }
</script>

<style>
  .login-container {
    max-width: 300px;
    margin: auto;
    padding: 1em;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  .error {
    color: red;
  }
</style>

<div class="login-container">
  <h2>Login</h2>
  <form on:submit={handleSubmit}>
    <div>
      <label for="username">Username:</label>
      <input type="text" id="username" bind:value={username} required />
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" id="password" bind:value={password} required />
    </div>
    {#if errorMessage}
      <p class="error">{errorMessage}</p>
    {/if}
    <button type="submit">Login</button>
  </form>
</div>