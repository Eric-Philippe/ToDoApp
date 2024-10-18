<script>
    import { isAuthenticated } from '../store/auth';
    import { wantsToSign } from '../store/wantsToSign';
  
    let username = '';
    let password = '';
    let errorMessage = '';
  
    async function handleSubmit(event) {
      event.preventDefault();
      errorMessage = '';
  
      try {
        const response = await fetch('http://localhost:3000/api/v1/auth/signup', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username, password }),
        });
  
        if (!response.ok) {
          throw new Error('An error occurred');
        }
  
        const data = await response.json();
        isAuthenticated.set(true);
        wantsToSign.set(0);
        localStorage.setItem('token', data.accessToken);
      } catch (error) {
        errorMessage = error.message;
      }
    }
  
    function handleSignUp() {
      wantsToSign.set(0);
    }
  </script>
  
  <div class="min-h-screen bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 flex items-center justify-center px-4">
    <div class="max-w-md w-full bg-white p-8 rounded-lg shadow-lg">
      <h2 class="text-center text-2xl font-bold text-gray-800 mb-6">Create an account</h2>
  
      <form on:submit={handleSubmit} class="space-y-4">
        <div class="relative">
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <input
            type="text"
            id="username"
            bind:value={username}
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          />
        </div>
  
        <div class="relative">
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <div class="mt-1 relative">
            <input
              type="password"
              id="password"
              bind:value={password}
              required
              class="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            />
          </div>
        </div>
  
        {#if errorMessage}
          <div class="flex items-center text-red-600 space-x-2 mt-2">
            <i class="fas fa-exclamation-circle"></i>
            <p class="text-sm">{errorMessage}</p>
          </div>
        {/if}
  
        <button
          type="submit"
          class="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded-md transition"
        >
          Create
        </button>
      </form>
  
    <button
      on:click={handleSignUp}
      class="w-full mt-4 bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded-md transition"
    >
      Already have an account? Sign in
    </button>
    </div>
  </div>
  
  <style>
    @keyframes fadeIn {
      from {
        opacity: 0;
        transform: scale(0.95);
      }
      to {
        opacity: 1;
        transform: scale(1);
      }
    }
  </style>
  