<script>
  import { isAuthenticated } from './store/auth';
  import Home from './pages/Home.svelte';
  import Login from './pages/Login.svelte';

  let authenticated = false;

  $: isAuthenticated.subscribe(value => {
    authenticated = value;
  });
</script>

<main>
  {#if authenticated}
    <Home />
  {:else}
    <Login />
  {/if}
</main>