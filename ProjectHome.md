This is a part of [JHUB1 Online](https://code.google.com/p/jhub1online) project.

This part of the project covers remote client called "Agent".

# Project Introduction #
Agent is perhaps the most important part of the [JHUB1 Online](https://code.google.com/p/jhub1online) System.
Agent is working as an independent program on the clients systems therefore it must fulfil these 2 basic requirements:
  1. be small and lightweight for the operating system as it is primarily going to be working on small "embeded" PCs (as much as it can be done in JAVA).
  1. be extremely resilient and resistant for external issues that might influence its stability or just reliable.

## What is AGENT for ##
Agent helps to maintain instant bi direction communication with [JHUB1 Online](https://code.google.com/p/jhub1online) system that helps in reading and setting states or values to/from local devices.

### Hff ###
Doesn't have local persistence, all the config can be modified and saved via web interface
## Brief introduction to the solution ##
To achieve these objectives the agent is build based on very unique architecture that keeps all the important components as loosely coupled as possible, running in separate threads and controlled by dedicated "MANAGER" thread.

"MANAGER" thread's is responsible for keeping the thread alive "no matter what" it also allows to change configuration of the component without restarting the whole Agent's ecosystem.

For instance: The change of the configuration is particularly important for network components that might need to be reconfigured (change port or IP) all of this can be done during runtime without any major risk of losing data being processed.


> that is communicating with the server over XMPP protocol.