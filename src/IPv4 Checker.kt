fun isValidIpv4(ip: String): Boolean {
    var dotCount = 0
    var numStart = 0
    val length = ip.length

    if (length == 0 || ip[0] == '.' || ip[length - 1] == '.') return false

    var i = 0
    while (i < length) {
        if (ip[i] == '.') {
            dotCount++
            if (dotCount > 3 || i == numStart) return false
            if (i - numStart > 1 && ip[numStart] == '0') return false

            val num = ip.substring(numStart, i).toIntOrNull() ?: return false
            if (num !in 0..255) return false

            numStart = i + 1
        } else if (ip[i] !in '0'..'9') {
            return false
        }
        i++
    }

    if (dotCount != 3 || numStart >= length) return false
    if (length - numStart > 1 && ip[numStart] == '0') return false

    val num = ip.substring(numStart, length).toIntOrNull() ?: return false
    if (num !in 0..255) return false

    return true
}


// different delimiter // random string
fun main() {
    // Valid IPv4 addresses (should return true)
    println(check("Valid IPv4: ", isValidIpv4("192.168.1.1"), true))
    println(check("Valid IPv4: ", isValidIpv4("0.0.0.0"), true))
    println(check("Valid IPv4:", isValidIpv4("255.255.255.255"), true))
    println(check("Valid IPv4: ", isValidIpv4("1.2.3.4"), true))
    println(check("Valid IPv4: ", isValidIpv4("172.16.254.1"), true))

    // Invalid IPv4 addresses (should return false)
    // Wrong number of segments
    println(check("Invalid IPv4:  (too few segments)", isValidIpv4("192.168.1"), false))
    println(check("Invalid IPv4: (too many segments)", isValidIpv4("192.168.1.1.1"), false))
    println(check("Invalid IPv4:  (empty string)", isValidIpv4(""), false))

    // Out-of-range segments
    println(check("Invalid IPv4:  (segment > 255)", isValidIpv4("256.168.1.1"), false))
    println(check("Invalid IPv4:  (segment > 255)", isValidIpv4("192.168.1.256"), false))
    println(check("Invalid IPv4:  (negative segment)", isValidIpv4("192.168.1.-1"), false))

    // Leading zeros
    println(check("Invalid IPv4:  (leading zeros)", isValidIpv4("192.168.01.1"), false))
    println(check("Invalid IPv4:  (leading zeros)", isValidIpv4("192.168.001.1"), false))

    // Non-numeric segments and invalid characters
    println(check("Invalid IPv4:  (non-numeric segment)", isValidIpv4("192.168.1.a"), false))
    println(check("Invalid IPv4:  (trailing dot)", isValidIpv4("192.168.1.1."), false))
    println(check("Invalid IPv4:  (leading dot)", isValidIpv4(".192.168.1.1"), false))

    // Empty segments and malformed inputs
    println(check("Invalid IPv4:  (empty segment)", isValidIpv4("192..1.1"), false))
    println(check("Invalid IPv4:  (empty segment)", isValidIpv4("192.168..1"), false))

    // Spaces and other invalid formats
    println(check("Invalid IPv4:  (space at end)", isValidIpv4("192.168.1.1 "), false))
    println(check("Invalid IPv4:  (port number)", isValidIpv4("192.168.1.1:80"), false))
}