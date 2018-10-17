<?xml version="1.0" encoding="UTF-8" standalone="no"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>CCA</title>
                <h3>CCA Budget book</h3>
            </head>
            <body>
                <xsl:for-each select="ccabook/ccas">
                            <xsl:if test="name='Choir'">
                        <p>
                            CCA:
                            <xsl:value-of select="name"/>
                            <br/>
                            Head:
                            <xsl:value-of select="head"/>
                            <br/>
                            Vice-Head:
                            <xsl:value-of select="viceHead"/>
                            <br/>
                        </p>
                        <p>
                            Budget:
                            <xsl:value-of select="budget"/>
                            <br/>
                            OutStanding:
                            <xsl:value-of select="outstanding"/>
                            <br/>
                        </p>
                        <table border="1">
                            <tr>
                                <th>Transaction</th>
                            </tr>
                            <tr>
                                <td>
                                    <xsl:value-of select="transaction"/>
                                </td>
                            </tr>
                        </table>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
