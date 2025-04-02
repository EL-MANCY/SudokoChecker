fun isValidIpv4(ipAddress: String): Boolean {
    var dotCounter = 0 // Tracks the number of dots in the IP address
    var segmentStartIndex = 0 // Tracks the starting index of the current segment
    val totalLength = ipAddress.length

    // Basic checks: empty string, starts or ends with a dot
    if (totalLength == 0 || ipAddress[0] == '.' || ipAddress[totalLength - 1] == '.') return false

    var currentIndex = 0
    while (currentIndex < totalLength) {
        if (ipAddress[currentIndex] == '.') {
            dotCounter++
            // Too many dots or empty segment
            if (dotCounter > 3 || currentIndex == segmentStartIndex) return false

            // Check for leading zeros in multi-digit numbers
            if (currentIndex - segmentStartIndex > 1 && ipAddress[segmentStartIndex] == '0') return false

            // Extract the segment and validate its range
            val segmentValue = ipAddress.substring(segmentStartIndex, currentIndex).toIntOrNull() ?: return false
            if (segmentValue !in 0..255) return false

            // Move to the next segment
            segmentStartIndex = currentIndex + 1
        } else if (ipAddress[currentIndex] !in '0'..'9') {
            // Invalid character detected (not a digit or dot)
            return false
        }
        currentIndex++
    }

    // The address must contain exactly 3 dots, and the last segment must not be empty
    if (dotCounter != 3 || segmentStartIndex >= totalLength) return false

    // Check the last segment for leading zeros
    if (totalLength - segmentStartIndex > 1 && ipAddress[segmentStartIndex] == '0') return false

    // Extract and validate the last segment
    val lastSegmentValue = ipAddress.substring(segmentStartIndex, totalLength).toIntOrNull() ?: return false
    if (lastSegmentValue !in 0..255) return false

    return true // If all checks pass, it's a valid IPv4 address
}


// different delimiter // random string
fun main() {
    // Valid IPv4 addresses (should return true)
    println(
        check(
            "Valid IPv4: ",
            isValidIpv4("192.168.1.1"),
            true
        )
    )
    println(
        check(
            "Valid IPv4: ",
            isValidIpv4("0.0.0.0"),
            true
        )
    )
    println(
        check(
            "Valid IPv4:",
            isValidIpv4("255.255.255.255"),
            true
        )
    )
    println(
        check(
            "Valid IPv4: ",
            isValidIpv4("1.2.3.4"),
            true
        )
    )
    println(
        check(
            "Valid IPv4: ",
            isValidIpv4("172.16.254.1"),
            true
        )
    )

    // Invalid IPv4 addresses (should return false)
    // Wrong number of segments
    println(
        check(
            "Invalid IPv4:  (too few segments)",
            isValidIpv4("192.168.1"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4: (too many segments)",
            isValidIpv4("192.168.1.1.1"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (empty string)",
            isValidIpv4(""),
            false
        )
    )

    // Out-of-range segments
    println(
        check(
            "Invalid IPv4:  (segment > 255)",
            isValidIpv4("256.168.1.1"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (segment > 255)",
            isValidIpv4("192.168.1.256"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (negative segment)",
            isValidIpv4("192.168.1.-1"),
            false
        )
    )

    // Leading zeros
    println(
        check(
            "Invalid IPv4:  (leading zeros)",
            isValidIpv4("192.168.01.1"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (leading zeros)",
            isValidIpv4("192.168.001.1"),
            false
        )
    )

    // Non-numeric segments and invalid characters
    println(
        check(
            "Invalid IPv4:  (non-numeric segment)",
            isValidIpv4("192.168.1.a"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (trailing dot)",
            isValidIpv4("192.168.1.1."),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (leading dot)",
            isValidIpv4(".192.168.1.1"),
            false
        )
    )

    // Empty segments and malformed inputs
    println(
        check(
            "Invalid IPv4:  (empty segment)",
            isValidIpv4("192..1.1"),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (empty segment)",
            isValidIpv4("192.168..1"),
            false
        )
    )

    // Spaces and other invalid formats
    println(
        check(
            "Invalid IPv4:  (space at end)",
            isValidIpv4("192.168.1.1 "),
            false
        )
    )
    println(
        check(
            "Invalid IPv4:  (port number)",
            isValidIpv4("192.168.1.1:80"),
            false
        )
    )
}