<script>
  import { isAuthenticated } from './store/auth';
  import Home from './pages/Home.svelte';
  import Login from './pages/Login.svelte';
    import { wantsToSign } from './store/wantsToSign';
    import SignUp from './pages/SignUp.svelte';

  let authenticated = false;
  let signup = 0;

  $: isAuthenticated.subscribe((value) => {
    authenticated = value;
  });

  $: wantsToSign.subscribe((value) => {
    signup = value;
  });
</script>

<main>
  {#if authenticated}
    <Home />
  {:else}
    {#if signup === 1}
      <SignUp />
    {:else}
      <Login />
    {/if}
  {/if}
</main>
