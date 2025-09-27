<script lang="ts">
  import "../app.css";
  import favicon from "$lib/assets/favicon.svg";
  import { ModeWatcher } from "mode-watcher";
  import ThemeToggle from "$lib/components/theme-toggle.svelte";

  let { children } = $props();
</script>

<svelte:head>
  <link rel="icon" href={favicon} />
  <title>File Processor</title>
</svelte:head>

<ModeWatcher />
<ThemeToggle />
{@render children?.()}
