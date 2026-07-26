@echo off
echo Start MongoDB
start "" "C:\Users\dani_\AppData\Local\MongoDBCompass\MongoDBCompass.exe"
echo Start Backend...
start cmd /k "cd backend && gradle bootRun"
echo Start Frontend...
start cmd /k "cd frontend && npm run dev"