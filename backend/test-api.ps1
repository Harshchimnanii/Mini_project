Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "Running REST API Verification for Student API" -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan

# Helper to format JSON response
function Format-Response($response) {
    return $response | ConvertTo-Json -Depth 5
}

# Helper to print Section
function Print-Section($title) {
    Write-Host ""
    Write-Host ">>> $title <<<" -ForegroundColor Yellow
}

# 1. GET ALL STUDENTS
Print-Section "1. GET /api/students (All Students)"
$res1 = Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Get
Format-Response $res1

# 2. GET STUDENT BY ID
Print-Section "2. GET /api/students/1 (Student with ID 1)"
$res2 = Invoke-RestMethod -Uri "http://localhost:8080/api/students/1" -Method Get
Format-Response $res2

# 3. GET STUDENT BY ROLL NUMBER
Print-Section "3. GET /api/students/roll/CS101 (Student with Roll Number CS101)"
$res3 = Invoke-RestMethod -Uri "http://localhost:8080/api/students/roll/CS101" -Method Get
Format-Response $res3

# 4. POST CREATE NEW STUDENT (Success)
Print-Section "4. POST /api/students (Add new student CS105)"
$newStudent = @{
    name = "Karan Johar"
    rollNumber = "CS105"
    course = "Cloud Computing"
    marks = 88.0
    attendance = 91.5
} | ConvertTo-Json
$res4 = Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Post -Body $newStudent -ContentType "application/json"
Format-Response $res4

# 5. POST CREATE STUDENT (Failure - Duplicate Roll Number CS101)
Print-Section "5. POST /api/students (Testing Duplicate Roll Number CS101 Exception)"
try {
    $dupStudent = @{
        name = "Duplicate Student"
        rollNumber = "CS101"
        course = "Science"
        marks = 70.0
        attendance = 80.0
    } | ConvertTo-Json
    Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Post -Body $dupStudent -ContentType "application/json"
} catch {
    Write-Host "Caught expected Duplicate Exception:" -ForegroundColor Green
    $stream = $_.Exception.Response.GetResponseStream()
    $reader = New-Object System.IO.StreamReader($stream)
    $reader.ReadToEnd()
}

# 6. POST CREATE STUDENT (Failure - Validation Error: Marks > 100)
Print-Section "6. POST /api/students (Testing Validation constraint: Marks = 150)"
try {
    $invalidStudent = @{
        name = "Invalid Student"
        rollNumber = "CS109"
        course = "Maths"
        marks = 150.0
        attendance = 80.0
    } | ConvertTo-Json
    Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Post -Body $invalidStudent -ContentType "application/json"
} catch {
    Write-Host "Caught expected Validation Exception:" -ForegroundColor Green
    $stream = $_.Exception.Response.GetResponseStream()
    $reader = New-Object System.IO.StreamReader($stream)
    $reader.ReadToEnd()
}

# 7. PUT UPDATE STUDENT
Print-Section "7. PUT /api/students/1 (Updating Amit Patel's name and marks)"
$updateStudent = @{
    name = "Amit Patel Updated"
    rollNumber = "CS101"
    course = "Computer Science & Eng"
    marks = 89.0
    attendance = 95.0
} | ConvertTo-Json
$res7 = Invoke-RestMethod -Uri "http://localhost:8080/api/students/1" -Method Put -Body $updateStudent -ContentType "application/json"
Format-Response $res7

# 8. DELETE STUDENT
Print-Section "8. DELETE /api/students/3 (Deleting Rohan Das)"
Invoke-RestMethod -Uri "http://localhost:8080/api/students/3" -Method Delete
Write-Host "Deleted student with ID 3 successfully" -ForegroundColor Green

# 9. VERIFY DELETE
Print-Section "9. GET /api/students (Verify deleted student is gone)"
$res9 = Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Get
Format-Response $res9
