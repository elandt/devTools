# Course Notes

## Prerquiesites

* HTML/CSS
* JavaScript (including ES2015/ES6)
* Commandline/Node.js (helpful, not required)

## Definitions/Acronyms

* DOM = document object model --> in-mem representation of the web page
* Computed property = something that's derived or calculated based on existing properties or other computed properties
  * In practice, they're similar to methods, but have some organization and performance advantages
    * Computed properties are cached and only recalculated when necessary rather than being invoked everytime the part of the DOM using that method is rerendered

## What problems does Vue.js solve?

* Managing complexity of keeping things in sync for the UI

### Vue.js is

* Reactive
* Progressive
* Declarative
* Composable

## Modifiers

* `.trim` - trims leading and trailing whitespace
* `.lazy` - changes from handling the input event to the change event
* `.number` - casts to numbers (integers specifically?)
* `.prevent` - prevents default browser behavior (specifically of form submission and page refresh?)
* `.stop` - stop propagation of an event
* `.self` - trigger handler only if the target is this element
* `.once` - only trigger this event once at most

## Code Snippets

```lang=html
<div class="user-input">
    <!-- Using `:value` is a one-way binding (Vue to DOM), using `v-model` we have a two-way binding -->
    <!-- The native input event is raised every time a character is typed, allowing the input to immediately impact other elements that use/reference the userInput -->
    Type Something: <input type="text" v-model="userInput" >
</div>
```

Checkboxes using named properties for binding data rather than binding to an array

```lang=html
<div>
    <input v-model="reverse" true-value="on"  false-value="off" type="checkbox" id="reverse" value="reverse">
    <label for="reverse">Include reverse cards</label>
</div>

<div>
    <input v-model="time_limit" type="checkbox" id="time_limit" value="time_limit">
    <label for="time_limit">Use time limit</label>
</div>

<div>
    <input v-model="reminders" type="checkbox" id="reminders" value="reminders">
    <label for="reminders">Enable reminders</label>
</div>

...

<!-- in the data() of the vue app -->
reverse: false,
time_limit: false,
reminders: false
```

```lang=html
<!-- `v-if`, `v-else-if`, v-else` will determine whether the elements
    even exist in the DOM rather than if they exist, but don't
    display. This can be detrimental to performance if the elements
    are resource intensive or time-consuming to create or destroy -->
<h2 v-if="!flipped" v-text="front"></h2>
<div v-else v-html="back"></div>
```
## What's Next

* The Vue guide is a good reference
* Vue.js 3 Essential Training by Ray Villalobos
