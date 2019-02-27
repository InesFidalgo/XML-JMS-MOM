<?xml version="1.0" encoding="UTF-8"?>
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
	<head><title> Medalhas </title></head> 
		<body>
			<xsl:for-each select="//pais">
				<h1 style="font-size:30px">
					<xsl:value-of select="nome"/>  [<xsl:value-of select="@abreviatura"/>] ouro: <xsl:value-of select="@ouro"/> prata: <xsl:value-of select="@prata"/> bronze: <xsl:value-of select="@bronze"/><br/>
				</h1>
				<table border = "1">
					<tr>
						<th><b> Modalidade </b></th>
						<th><b> Tipo </b></th>
						<th><b> Nome </b></th>
						<th><b> Medalha </b></th>
					</tr>
					<xsl:for-each select="modalidades">
						<xsl:for-each select="modalidade">
							<tr>
								<td><b> <xsl:value-of select="@nome"/> </b></td>
							</tr>
							<xsl:for-each select="pessoa">
								<tr>
									<th><b>  </b></th>
									<th><b> <xsl:value-of select="@tipo"/><br/> </b></th>
									<th><b> <xsl:value-of select="@nome"/><br/> </b></th>
									<th><b> <xsl:value-of select="@medalha"/><br/> </b></th>
								</tr>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>
				</table>
			</xsl:for-each>
		</body>
</html>