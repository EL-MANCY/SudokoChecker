fun isValidIpv4(ip: String): Boolean {
    // Split the input string by dots to get the segments
    val segments = ip.split('.')

    // Check if there are exactly 4 segments
    if (segments.size != 4) return false

    // Validate each segment
    for (segment in segments) {
        // Ensure each segment is numeric and does not have leading zeros, unless it's just '0'
        if (!isValidSegment(segment)) return false
    }

    return true
}

// Function to validate a single segment of the IP address
fun isValidSegment(segment: String): Boolean {
    // Check if segment is numeric
    if (segment.isEmpty() || segment.length > 3 || segment.toIntOrNull() == null) return false

    // Check if the segment is within the valid range (0 to 255)
    val segmentInt = segment.toInt()
    if (segmentInt !in 0..255) return false

    // Check if there are leading zeros in a segment that is not '0'
    return !(segment.startsWith("0") && segment != "0")
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