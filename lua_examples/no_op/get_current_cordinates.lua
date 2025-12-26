local pos = player:getPos()

local x = pos:getX()
local y = pos:getY()
local z = pos:getZ()

local msg = "local: " .. "X: " .. x .. " Y: " .. y .. " Z: " .. z

player:sendMessage(msg)