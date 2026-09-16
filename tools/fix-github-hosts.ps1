# 修复 hosts 中过期的 GitHub IP 映射（需要以管理员身份运行）
# 原因：hosts 把 github.com 固定到 140.82.112.4，该 IP 的 443 端口已无法连通，
#      而 DNS 实际解析出的 20.205.243.166 是通的。清除 hosts 相关行即可恢复 git push。
$hostsPath  = "$env:WINDIR\System32\drivers\etc\hosts"
$backupPath = "$env:USERPROFILE\Desktop\lab1-hosts-backup.txt"

Copy-Item $hostsPath $backupPath -Force
Write-Host "1. 已备份原 hosts 到: $backupPath" -ForegroundColor Green

$kept = Get-Content $hostsPath | Where-Object { $_ -notmatch 'github' }
Set-Content -Path $hostsPath -Value $kept -Encoding ASCII -Force

$remaining = (Select-String -Path $hostsPath -Pattern 'github' -ErrorAction SilentlyContinue | Measure-Object).Count
Write-Host "2. hosts 中残留 github 行数: $remaining （0 表示已清理干净）" -ForegroundColor Green
Write-Host ""
Write-Host "处理完成！此窗口将在 30 秒后自动关闭。" -ForegroundColor Yellow
Start-Sleep -Seconds 30
