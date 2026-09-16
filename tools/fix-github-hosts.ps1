# 修复 hosts 中过期的 GitHub IP 映射
# 原因：hosts 将 github.com 固定到 140.82.112.4，该 IP 已无法连通（TCP 443 超时），
# 而 DNS 实际解析出的 20.205.243.166 是通的。清除 hosts 中相关行即可恢复访问。
$hostsPath = "$env:WINDIR\System32\drivers\etc\hosts"
$backupPath = "$env:USERPROFILE\Desktop\lab1-hosts-backup.txt"

Copy-Item $hostsPath $backupPath -Force
Write-Host "已备份原 hosts 到: $backupPath"

$kept = Get-Content $hostsPath | Where-Object { $_ -notmatch 'github' }
Set-Content -Path $hostsPath -Value $kept -Encoding ASCII -Force

$remaining = (Select-String -Path $hostsPath -Pattern 'github' -ErrorAction SilentlyContinue | Measure-Object).Count
Write-Host "hosts 中残留 github 行数: $remaining"
Write-Host "处理完成，5 秒后自动关闭窗口。"
Start-Sleep -Seconds 5
