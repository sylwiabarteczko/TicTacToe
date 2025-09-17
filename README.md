## 🔑 API Key Setup (AI)

This project integrates with the [OpenRouter](https://openrouter.ai) API to provide an AI opponent in Tic Tac Toe.  
To use this feature, you need to create your own API key and configure it in the application.

### 1. Create an OpenRouter account
- Go to [https://openrouter.ai](https://openrouter.ai)  
- Sign up or log in.  

### 2. Generate your API key
- Open [https://openrouter.ai/keys](https://openrouter.ai/keys)  
- Click **Create Key**  
- Copy the generated key.  

### 3. Save the key to a file
- Create a text file named `openrouter.key`  
- Paste your API key inside (only the key, no extra spaces or quotes).  
- Save the file in your project folder (⚠️ do not commit this file to GitHub).  

### 4. Configure the path in the application
- Place your `openrouter.key` file in the root of the project (next to `README.md` and `pom.xml`).  
- Then open `src/main/resources/application.properties` and add:
  
```properties
openrouter.key-path=openrouter.key
