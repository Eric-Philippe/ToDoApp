<script>
  import { onMount } from 'svelte';
  import { isAuthenticated } from '../store/auth';
  import { writable } from 'svelte/store';

  let todos = writable([]);
  let showForm = writable(false);
  let newTodo = { title: '', description: '' };
  let token = localStorage.getItem('token');

  onMount(async () => {
    if (!token) {
      return;
    }

    try {
      const response = await fetch('http://localhost:3000/api/v1/todo', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      todos.set(data);
    } catch (error) {
      console.error('Failed to fetch todos:', error);
    }
  });

  async function handleDelete(id) {
    try {
      await fetch(`http://localhost:3000/api/v1/todo/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      todos.update(current => current.filter(todo => todo.id !== id));
    } catch (error) {
      console.error('Failed to delete todo:', error);
    }
  }

  async function handleAdd(event) {
    event.preventDefault();
    try {
      const response = await fetch('http://localhost:3000/api/v1/todo', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(newTodo)
      });
      const data = await response.json();
      todos.update(current => [...current, data]);
      showForm.set(false);
      newTodo = { title: '', description: '' };
    } catch (error) {
      console.error('Failed to add todo:', error);
    }
  }

  function handleLogout() {
    localStorage.removeItem('token');
    isAuthenticated.set(false);
  }
</script>

<style>
  .todo-container {
    max-width: 600px;
    margin: auto;
    padding: 1em;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  .todo-item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1em;
  }
  .todo-item button {
    margin-left: 1em;
  }
  .form-container {
    margin-top: 2em;
  }
</style>

<div class="todo-container">
  <h1>TODO List</h1>
  <button on:click={() => showForm.set(true)}>Add TODO</button>
  <button on:click={handleLogout}>Logout</button>
  {#if $showForm}
    <div class="form-container">
      <form on:submit={handleAdd}>
        <div>
          <label for="title">Title:</label>
          <input type="text" id="title" bind:value={newTodo.title} required />
        </div>
        <div>
          <label for="description">Description:</label>
          <input type="text" id="description" bind:value={newTodo.description} required />
        </div>
        <button type="submit">Add</button>
        <button type="button" on:click={() => showForm.set(false)}>Cancel</button>
      </form>
    </div>
  {/if}
  {#each $todos as todo}
    <div class="todo-item">
      <div>
        <h3>{todo.title}</h3>
        <p>{todo.description}</p>
      </div>
      <div>
        <button on:click={() => handleDelete(todo.id)}>Delete</button>
        <!-- Add edit functionality here if needed -->
      </div>
    </div>
  {/each}
</div>