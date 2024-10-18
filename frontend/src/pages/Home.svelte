<script>
  import { onMount } from 'svelte';
  import { writable } from 'svelte/store';
  import { isAuthenticated } from '../store/auth';
    import { wantsToSign } from '../store/wantsToSign';

  let todos = writable([]);
  let showForm = writable(false);
  let newTodo = { title: '', description: '', completed: false };
  let token = localStorage.getItem('token');

  onMount(async () => {
    const cachedTodos = localStorage.getItem('todos');
    if (cachedTodos) {
      todos.set(JSON.parse(cachedTodos));
    } else if (token) {
      try {
        const response = await fetch('http://localhost:3000/api/v1/todo', {
          headers: { Authorization: `Bearer ${token}` },
        });
        const data = await response.json();
        todos.set(data);
        localStorage.setItem('todos', JSON.stringify(data));
      } catch (error) {
        console.error('Failed to fetch todos:', error);
      }
    }
  });

  async function handleDelete(id) {
    try {
      await fetch(`http://localhost:3000/api/v1/todo/${id}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` },
      });
      todos.update((current) => {
        const updatedTodos = current.filter((todo) => todo.id !== id);
        localStorage.setItem('todos', JSON.stringify(updatedTodos));
        return updatedTodos;
      });
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
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newTodo),
      });
      const data = await response.json();
      todos.update((current) => {
        const updatedTodos = [...current, data];
        localStorage.setItem('todos', JSON.stringify(updatedTodos));
        return updatedTodos;
      });
      showForm.set(false);
      newTodo = { title: '', description: '', completed: false };
    } catch (error) {
      console.error('Failed to add todo:', error);
    }
  }

  function handleToggleComplete(todo) {
    todo.completed = !todo.completed;
    todos.update((current) => {
      const updatedTodos = current.map((t) => (t.id === todo.id ? todo : t));
      localStorage.setItem('todos', JSON.stringify(updatedTodos));
      return updatedTodos;
    });
  }

  function handleLogout() {
    localStorage.clear();
    isAuthenticated.set(false);
    wantsToSign.set(0);
  }
</script>

<div class="min-h-screen bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 p-10">
  <div class="max-w-2xl mx-auto bg-white rounded-lg shadow-xl p-8">
    <h1 class="text-3xl font-bold text-center text-gray-800 mb-8">🌟 My TODO List</h1>

    <div class="flex justify-between mb-6">
      <button
        class="flex items-center space-x-2 text-white bg-green-500 hover:bg-green-600 px-4 py-2 rounded-md transition"
        on:click={() => showForm.set(true)}
      >
        <span>Add TODO</span>
      </button>
      <button
        class="flex items-center space-x-2 text-white bg-red-500 hover:bg-red-600 px-4 py-2 rounded-md transition"
        on:click={handleLogout}
      >
        <span>Logout</span>
      </button>
    </div>

    {#if $showForm}
      <div class="bg-gray-100 p-4 rounded-lg mb-6">
        <form on:submit={handleAdd}>
          <div class="mb-4">
            <label for="todo-title" class="block text-sm font-medium text-gray-700">Title</label>
            <input
              id="todo-title"
              type="text"
              class="mt-1 block w-full p-2 border rounded-md"
              bind:value={newTodo.title}
              required
            />
          </div>
          <div class="mb-4">
            <label for="todo-description" class="block text-sm font-medium text-gray-700">Description</label>
            <input
              id="todo-description"
              type="text"
              class="mt-1 block w-full p-2 border rounded-md"
              bind:value={newTodo.description}
              required
            />
          </div>
          <div class="flex justify-end space-x-2">
            <button
              type="submit"
              class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition"
            >
              Add
            </button>
            <button
              type="button"
              class="bg-gray-300 px-4 py-2 rounded-md hover:bg-gray-400 transition"
              on:click={() => showForm.set(false)}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    {/if}

    <div class="space-y-4">
      {#each $todos as todo}
        <div class="todo-item bg-gray-50 p-4 rounded-md shadow flex justify-between items-center">
          <div>
            <input
              type="checkbox"
              checked={todo.completed}
              on:change={() => handleToggleComplete(todo)}
            />
            <h3 class="text-lg font-semibold {todo.completed ? 'line-through' : ''}">{todo.title}</h3>
            <p class="text-gray-600 {todo.completed ? 'line-through' : ''}">{todo.description}</p>
          </div>
          <button
            class="text-red-500 hover:text-red-600 transition"
            on:click={() => handleDelete(todo.id)}
          >
            <i class="fas fa-trash-alt"></i> Delete
          </button>
        </div>
      {/each}
    </div>
  </div>
</div>

<style>
  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .line-through {
    text-decoration: line-through;
  }

  .todo-item {
    transition: transform 0.3s ease, box-shadow 0.3s ease;
  }

  .todo-item:hover {
    transform: scale(1.02);
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  }
</style>